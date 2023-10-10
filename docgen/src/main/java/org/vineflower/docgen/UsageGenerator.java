/*
 * Copyright (c) 2023 Vineflower team and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.vineflower.docgen;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.slf4j.Logger;
import org.vineflower.docgen.util.Logging;

public class UsageGenerator {
    private static final Logger LOGGER = Logging.logger();
    private static final int PSF_MASK = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
    private static final String ANNOTATION_PREFIX = "org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences$";
    private static final boolean HAS_LEGACY_SHORT_NAME;

    static {
        boolean hasShortName = false;
        try {
            Class.forName(ANNOTATION_PREFIX + "ShortName");
            hasShortName = true;
        } catch (final ClassNotFoundException ignored) {
        }
        HAS_LEGACY_SHORT_NAME = hasShortName;
    }

    record Problem(String argumentName, String message) {}

    record ArgumentInfo(String argumentName, String friendlyName, String legacyShortName, String description, String defaultValue, String type) {
        static ArgumentInfo fromField(final Field field, final Consumer<Problem> problemConsumer) {
            final String argumentName;
            try {
                argumentName = (String) field.get(null);
            } catch (IllegalAccessException e) {
                problemConsumer.accept(new Problem(field.getName(), "could not read field value"));
                return null;
            }

            String friendlyName = annotationValueOrNull(field,"Name");
            if (friendlyName == null) {
                problemConsumer.accept(new Problem(argumentName, "missing @Name annotation"));
            }
            String description = annotationValueOrNull(field,"Description");
            if (description == null) {
                problemConsumer.accept(new Problem(argumentName, "missing @Description annotation"));
            }
            String defaultValue = valueOfOrNull(IFernflowerPreferences.getDefaults().get(argumentName));
            // 1.10+
            String legacyShortName = annotationValueOrNull(field, "ShortName");
            String type = annotationValueOrNull(field, "Type");

            return new ArgumentInfo(argumentName, friendlyName, legacyShortName, description, defaultValue, type);
        }
    }


    public static boolean generate(final Path usageFile, final PebbleEngine engine) throws IOException {
        final List<Problem> problems = new ArrayList<>();
        final List<ArgumentInfo> info = Arrays.stream(IFernflowerPreferences.class.getFields())
            .filter(UsageGenerator::isPublicStaticFinalString)
            .filter(field -> field.isAnnotationPresent(IFernflowerPreferences.Name.class)) // basic sanity check
            .map(f -> ArgumentInfo.fromField(f, problems::add))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        info.sort(Comparator.comparing(ArgumentInfo::argumentName));

        if (!problems.isEmpty()) {
            LOGGER.error("There were problems discovering information about argument usage:");
            for (final var problem : problems) {
                LOGGER.error("- '{}': {}", problem.argumentName(), problem.message());
            }
            return false;
        }

        final PebbleTemplate tpl = engine.getTemplate("usage.peb");
        try (final Writer writer = Files.newBufferedWriter(usageFile, StandardCharsets.UTF_8)) {
            tpl.evaluate(writer, Map.of("args", info, "hasLegacyShortNames", HAS_LEGACY_SHORT_NAME));
        }
        return true;
    }

    private static boolean isPublicStaticFinalString(final Field field) {
        return String.class.equals(field.getType()) && (field.getModifiers() & PSF_MASK) == PSF_MASK;
    }

    private static String valueOfOrNull(final Object input) {
        return input == null ? null : String.valueOf(input);
    }

    private static String annotationValueOrNull(final AnnotatedElement element, final String annotation) {
        final String qualifiedName = ANNOTATION_PREFIX + annotation;
        for (final Annotation a : element.getAnnotations()) {
            if (a.annotationType().getName().equals(qualifiedName)) {
                try {
                    return (String) a.getClass().getMethod("value").invoke(a);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException("Failed to read value field from annotation " + a.annotationType().getName() + " on " + element, e);
                }
            }
        }
        return null;
    }
}
