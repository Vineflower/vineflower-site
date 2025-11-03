==========
Vineflower
==========

Vineflower is a modern Java decompiler aiming to be as accurate as possible, while not sacrificing the readability of the generated code. Vineflower supports modern Java (J21+), automatic reformatting of output code, and multithreaded decompilation. A detailed comparison of Vineflower's output compared to other decompilers can be found on the `output comparison <output-comparison>`_ page.

The main repository for Vineflower is located on GitHub at `Vineflower/vineflower <https://github.com/Vineflower/vineflower>`_.

Downloads
============

The primary mode of distribution for Vineflower is the jar, which can be found on the `releases page <https://github.com/Vineflower/vineflower/releases>`_ on GitHub and on Maven Central. There is also an `Intellij plugin <https://plugins.jetbrains.com/plugin/18032-quiltflower>`_ for use. New releases are announced on the `mailing lists <https://vineflower.org/mailing-lists/announce>`_.

Running Vineflower requires a Java 17 or newer JVM, though classpath context can be loaded from any other JVM using the ``-jrt`` CLI parameter. Information on using Vineflower from the command line can be accessed from the `command line usage <usage>`_ page.

Information on using Vineflower from code as a library can be accessed from the `code usage <usage-code>`_ page.

Socials
=========

To get in touch for queries, please check out the socials_ page.


.. the auto-generated TOC already exists in the sidebar, let's not show it here

.. toctree::
   :maxdepth: 2
   :hidden:

   usage
   usage-code
   output-comparison
   socials
   contributing
   version-history

.. _socials: socials
.. _usage: usage
.. _usage-code: usage-code
.. _output-comparison: output-comparison
