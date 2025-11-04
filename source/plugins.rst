==================
Plugin Development
==================

Starting with version 1.10, Vineflower includes an experimental plugin system to allow users to modify Vineflower's output without needing to change Vineflower itself. Plugins can be used to rename local variables, modify the statement structure, or even implement new languages. Vineflower comes with some builtin plugins, and allows the loading of plugins at runtime. While the system is still experimental, the API is relatively stable.


Builtin Plugins
---------------

When not using the ``-slim`` jar, Vineflower comes with these built-in plugins:

:`Kotlin <https://github.com/Vineflower/vineflower/tree/master/plugins/kotlin>`_: Provides an experimental decompiler for Kotlin classes
:`IDEA @NotNull <https://github.com/Vineflower/vineflower/tree/master/plugins/idea-not-null>`_: Provides decompilation for synthetic @NotNull bytecode injected by the Intellij IDEA compiler
:`Variable Renaming <https://github.com/Vineflower/vineflower/tree/master/plugins/variable-renaming>`_: Provides a modular variable renaming API, with two builtin defaults "jad" and "tiny"

Creating new plugins
--------------------

The primary way to create new plugins is to use the ServiceLoader API. To begin, create a main plugin class that implements ``org.jetbrains.java.decompiler.api.plugin.Plugin``. Fill in an ID and description. Next, add a file named ``org.jetbrains.java.decompiler.api.plugin.Plugin`` in the directory ``META-INF/services``. In this file, write the fully qualified name of your main plugin class. When it's on the classpath, your plugin should now be found through the ServiceLoader.


Modifying Java decompilation
----------------------------

The Java decompilation process is long and complex, but plugins have some hooks available to modify it. All code modification is done in a :java:`Pass`. A :java:`Pass` is a function that takes in the current statement structure and modifies it, returning whether or not the change was made. Creating a pass requires some understanding of Vineflower's internals, some which are documented in Vineflower's `ARCHITECTURE.md <https://github.com/Vineflower/vineflower/blob/master/ARCHITECTURE.md>`_.

After the pass is created, it has to be registered so that the decompiler knows when to run it. Passes are registered in the plugin method :java:`void registerJavaPasses(JavaPassRegistrar registrar)`. The registrar takes in a named pass and the location to run the pass in. The named pass can be constructed with :java:`NamedPass.of("MyPass", new MyPass())`. A useful pass location is :java:`JavaPassLocation.MAIN_LOOP`, but others can be chosen if needed. Be warned that returning :java:`true` in your pass will restart the main decomp loop, so always returning :java:`true` will mean decompilation will never finish. The precise location of when Java passes are run can be found in the main decompilation file, `MethodProcessor <https://github.com/Vineflower/vineflower/blob/master/src/org/jetbrains/java/decompiler/main/rels/MethodProcessor.java>`_. A full example of a Java modifying pass can be found in the `IDEA @NotNull plugin <https://github.com/Vineflower/vineflower/blob/master/plugins/idea-not-null/src/main/java/org/vineflower/ideanotnull/IdeaNotNullPlugin.java>`_.

Implementing new JVM languages
------------------------------

In addition to modifying the existing Java decompilation process, Vineflower also allows plugins to take over the decompilation process to implement new languages. A plugin can implement a :java:`LanguageSpec`, which implements various components of the decompiler, including the statement parser, main decompilation pass, and class writer. The most instructive reference for implementing a new JVM language is Vineflower's `Kotlin plugin <https://github.com/Vineflower/vineflower/blob/master/plugins/kotlin/src/main/java/org/vineflower/kotlin/KotlinPlugin.java>`_, which implements Kotlin decompilation on top of Vineflower.

Custom plugin loading
---------------------

By default Vineflower comes with two plugin loaders, the Jar-in-Jar loader for loading builtin plugins and the ServiceLoader plugin loader. A custom plugin loader can be implemented by adding a new :java:`PluginSource` implementation to :java:`PluginSources.PLUGIN_SOURCES`.

Is something missing?
---------------------
The plugin API is still in development, and the reality is that large parts of the decompiler was written with Java primarily in mind. If there is some behavior that is not yet exposed to plugin modification, we'd love to `hear from you <socials_>`_ to find a solution.

.. _socials: ../socials
