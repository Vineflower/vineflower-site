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
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import org.slf4j.Logger;
import org.vineflower.docgen.util.Logging;
import picocli.CommandLine;

public class DocGenerator {
    private static final Logger LOGGER = Logging.logger();

    static class Options {

        @CommandLine.Option(names = {"-t", "--target"}, description = "the directory to output generated documentation to", defaultValue = ".")
        Path targetDir;

        @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
        private boolean helpRequested = false;
    }

    public static void main(String[] args) throws IOException {
        final Options opts = new Options();
        new CommandLine(opts).parseArgs(args);

        Files.createDirectories(opts.targetDir);

        final var loader = new ClasspathLoader(DocGenerator.class.getClassLoader());
        loader.setPrefix("tmpl/");
        final var templateEngine = new PebbleEngine.Builder()
            .loader(loader)
            .defaultLocale(Locale.ENGLISH)
            .build();

        final var usageFile = opts.targetDir.resolve("usage.md");

        boolean success = false;
        try {
            success = UsageGenerator.generate(usageFile, templateEngine);
        } catch (final IOException ex) {
            LOGGER.error("Failed to write usage output!", ex);
            System.exit(1);
        }

        if (!success) {
            System.exit(1);
        }
    }
}
