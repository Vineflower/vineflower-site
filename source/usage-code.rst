============
Usage (Code)
============

To use vineflower from code, it needs to be added as a library. Vineflower is published on Maven Central, and can be accessed from build scripts like so:

.. tab-set::

   .. tab-item:: Maven
      :sync: maven

      .. code-block:: xml
        :substitutions:

         <dependency>
            <groupId>org.vineflower</groupId>
            <artifactId>vineflower</artifactId>
            <version>|version|</version>
         </dependency>

   .. tab-item:: Gradle (Groovy)
      :sync: gradle-groovy

      .. code-block:: groovy
        :substitutions:

         repositories {
            mavenCentral()
         }

         dependencies {
            implementation "org.vineflower:vineflower:|version|"
         }


   .. tab-item:: Gradle (Kotlin)
      :sync: gradle-kotlin

      .. code-block:: kotlin
        :substitutions:

         repositories {
            mavenCentral()
         }

         dependencies {
            implementation("org.vineflower:vineflower:|version|")
         }

Development (``-SNAPSHOT``) builds are published on the Sonatype OSS snapshots repository as well for testing purposes:

.. tab-set::

   .. tab-item:: Maven
      :sync: maven

      .. code:: xml

         <repositories>
             <repository>
                <id>sonatype-central-snapshots</id>
                <url>https://central.sonatype.com/repository/maven-snapshots/</url>
             </repository>
         </repositories>

   .. tab-item:: Gradle (Groovy)
      :sync: gradle-groovy

      .. code:: groovy

         repositories {
            maven {
                name = "sonatype-central-snapshots"
                url = "https://central.sonatype.com/repository/maven-snapshots/"
            }
         }

   .. tab-item:: Gradle (Kotlin)
      :sync: gradle-kotlin

      .. code:: kotlin

         repositories {
            maven(url = "https://central.sonatype.com/repository/maven-snapshots/") {
                name = "sonatype-central-snapshots"
            }
         }

Slim jars
---------
Vineflower also publishes a ``-slim`` artifact, which does not contain any plugins. It can be accessed by appending ``-slim`` to the version number. This can be used if the builtin plugins are not required, or if the smallest possible dependency size is needed.

Running the decompiler
----------------------

The main API for the decompiler is located at ``org.jetbrains.java.decompiler.api.Decompiler``. After a builder is constructed, inputs and outputs can be specified. After it's built, the ``decompile()`` method does the actual decompilation.

.. code-block:: java
  :substitutions:

  Decompiler decompiler = new Decompiler.Builder()
    .inputs(inputFile) // also accepts IContextSource
    .output(new SingleFileSaver(outputJar)) // also accepts DirectoryResultSaver, or a custom IResultSaver implementation
    .option(IFernflowerPreferences.INCLUDE_ENTIRE_CLASSPATH, true) // optional, change options for the decompiler
    .logger(new IFernflowerLogger() { ... }) // optional, provide a logger impl for the decompiler to use
    .libraries(libraryFile) // optional, include libraries to enhance the decompiler's analysis but not decompile
    .build();

  decompiler.decompile();
  // Done!


Backwards compatibility
-----------------------

Generally dependencies on Fernflower can be seamlessly swapped for Vineflower without changing the code if they only use Fernflower's informal public API, files in ``org.jetbrains.java.decompiler.main.extern``. Other files have been extensively modified.
