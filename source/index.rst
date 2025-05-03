==========
Vineflower
==========

Vineflower is a modern Java decompiler aiming to be as accurate as possible, while not sacrificing the readability of the generated code. Vineflower supports modern Java (J20+), automatic reformatting of output code, and multithreaded decompilation. The main repository for Vineflower is located on GitHub at `Vineflower/vineflower <https://github.com/Vineflower/vineflower>`_.

Downloads
============

The primary mode of distribution for Vineflower is the jar, which can be found on the `releases page <https://github.com/Vineflower/vineflower/releases>`_ on GitHub, which can also found on maven. There is also an `Intellij Plugin <https://plugins.jetbrains.com/plugin/18032-quiltflower>`_ for use.

Running Vineflower requires a Java 17 or newer JVM, though classpath context can be loaded from any other JVM using the ``-jrt`` CLI parameter.

Vineflower is published on Maven Central, and can be accessed from build scripts like so:

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
                <id>sonatype-oss-snapshots1</id>
                <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
             </repository>
         </repositories>

   .. tab-item:: Gradle (Groovy)
      :sync: gradle-groovy

      .. code:: groovy

         repositories {
            maven {
                name = "sonatype-oss-snapshots1"
                url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            }
         }

   .. tab-item:: Gradle (Kotlin)
      :sync: gradle-kotlin

      .. code:: kotlin

         repositories {
            maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
                name = "sonatype-oss-snapshots"
            }
         }

Socials
=========

To get in touch for queries, please check out the socials_ page.


.. the auto-generated TOC already exists in the sidebar, let's not show it here

.. toctree::
   :maxdepth: 2
   :hidden:

   usage
   socials
   contributing
   version-history

.. _socials: socials
