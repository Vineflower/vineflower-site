/*
 * Copyright (c) 2023-2025 Vineflower team and contributors
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

import io.pebbletemplates.pebble.utils.Pair;
import org.jetbrains.java.decompiler.api.DecompilerOption;
import org.jetbrains.java.decompiler.api.plugin.Plugin;
import org.jetbrains.java.decompiler.main.Init;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.slf4j.Logger;
import org.vineflower.docgen.util.Logging;

public class UsageGenerator {
    public static boolean generate(final Path usageFile, final PebbleEngine engine) throws IOException {
        Init.init();

        List<Pair<String, List<DecompilerOption>>> options = DecompilerOption.getAllByPlugin()
            .entrySet().stream()
            .sorted(Comparator.comparing(entry -> entry.getKey() != null ? entry.getKey().id() : ""))
            .map(entry -> {
                String pluginName = entry.getKey() != null ? "Plugin: " + entry.getKey().id() : null;
                List<DecompilerOption> pluginOptions = entry.getValue();
                return new Pair<>(pluginName, pluginOptions);
            })
            .toList();

        final PebbleTemplate tpl = engine.getTemplate("usage.peb");
        try (final Writer writer = Files.newBufferedWriter(usageFile, StandardCharsets.UTF_8)) {
            tpl.evaluate(writer, Map.of("groups", options));
        }
        return true;
    }
}
