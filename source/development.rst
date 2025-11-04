======================
Vineflower Development
======================

Contributions to Vineflower whether they be small bugfixes or additions of new features are always appreciated. A guideline for contributions can be found in the repository's `CONTRIBUTING.md <https://github.com/Vineflower/vineflower/blob/master/CONTRIBUTING.md>`_, and the `ARCHITECTURE.md <https://github.com/Vineflower/vineflower/blob/master/ARCHITECTURE.md>`_ is a useful guide for understanding the internals of the decompiler.

Setting up development
----------------------
After cloning the repository, make sure you ``git switch`` to the latest ``develop/1.xx.x` branch. The ``master`` branch is the stable version, and all development happens on ``develop`` branches. To build Vineflower, run ``./gradlew build`` (or ``.\gradlew.bat`` build on Windows). This will produce a compiled jar under ``./build/libs``. When creating new changes, make a new branch first.

Finding something to change
---------------------------

The `issue tracker <https://github.com/Vineflower/vineflower/issues>`_ has a lot of issues, big and small, that could use some work on. There are also many TODOs in the `main test class <https://github.com/Vineflower/vineflower/blob/master/test/org/jetbrains/java/decompiler/SingleClassesTest.java>`_. Before refactoring or changing large parts of the code, it's highly recommended to `get in touch <socials>`_ first.

Creating unit tests
-------------------

Vineflower uses an extensive unit test suite to ensure that any changes to the code don't have an adverse impact elsewhere. When fixing a bug or adding a new feature, a unit test should also be added or changed to verify the transformation. The unit tests work by decompiling the test classes and comparing them with the corresponding .dec file under ``testData/results``. To add a new test, first a domain for the test needs to be chosen. Domains specify what compiler version the test is compiled with, such as Java 8, 16, or 21. Add your new test Java file there. Next, it needs to be added in `SingleClassesTest.java <https://github.com/Vineflower/vineflower/blob/master/test/org/jetbrains/java/decompiler/SingleClassesTest.java>`_. When registering a new test, a flag set to test under also needs to be chosen. Generally, this is :java:`registerDefault()`. For tests involving generics, this is :java:`registerJavaRuntime()`. At the end of the register method, register your test: :java:`register(<DOMAIN>, "<TestExample>");`. Rerun all the tests, and the testing system will automatically compile your Java file and generate a .dec file for your test based on the compiled class.

.. note::

   For Intellij IDEA users, it is better to use the JUnit run configuration instead of the Gradle test run configuration. This is because the JUnit run configuration allows for focusing execution on a single test, while the Gradle one does not. To set up the JUnit run configuration, specify ``Class`` as the test type and ``org.jetbrains.java.decompiler.SingleClassesTest`` as the value. Make sure the module is ``vineflower.test``.  When using the JUnit configuration, you will need to manually provide a JDK 8 and JDK 16 home with ``-Djava.8.home=`` and ``-Djava.16.home=`` for the Java Runtime tests. You should also add ``Build, No Error Check`` as a pre-launch argument to ensure that the test classes are rebuilt when tests are run. Now, you are able to right click on a test in the UI to run that test specifically, allowing for more fine-grained debugging.

Test failures are expected when modifying the decompiler. If a failure is due to the decompilation changing in a beneficial manner, the .dec file should be changed to reflect the new decompiled output by pasting in the new decompilation or deleting the file to make the testing system regenerate it.


Debugging Vineflower
--------------------

Debugging Vineflower's many moving parts can be a challenge, but there are some debugging tools that could be useful. The primary debugging tool is the .dot file exporter. It can be activated by specifying a directory to ``-DDOT_EXPORT_DIR=``, such as ``build/dot``. When this argument is set, the decompiler creates a .dot graph of the statement structure at every point in the decompilation process. This takes a lot of time and can take a lot of space, so it's best done when focusing execution to one class. The alternative flag ``-DDOT_ERROR_EXPORT_DIR=`` can be specified to only export graphs if there is an error in the decompiler. These graphs can be visualized with any .dot file viewer, but a good online viewer is https://dreampuf.github.io/GraphvizOnline/.

In the dot graph the nodes are the statements, and the edges are the relationships between them. Statements contain statements inside them, creating a tree. The red arrows show this nesting behavior, where top level statements contain statements inside them. Black arrows are "regular" edges, which represent default control flow between statements. Blue edges are break edges, which represent a java break/return (forward jump) relation between statements. Green edges are continue edges, which represent a continue (backward jump) relation between statements.

Another debugging tool is the validation system. These are soft asserts that are disabled by default that check many different invariants in the decompiled code. It can be used to check if a new pass or change to an existing pass creates an invalid statement structure that could break future passes. To enable the validation checker, pass ``-DVALIDATE_DECOMPILED_CODE=true``.

When debugging a class with many different methods, a single method can be focused on by setting ``DEBUG_METHOD_FILTER`` in ``ClassWrapper.java``. Any methods with a different name will be skipped entirely.

The Intellij Coverage runner can also be used to see at a glance which code paths are taken and which are not. Statements can be dumped in the debugger with ``<stat>.toDebug()``.

For additional debugging help, our `social channels <socials>`_ have developers that can give pointers.

Submitting changes
------------------

When your changes are completed, it can be submitted on GitHub as a pull request. Make sure you're targeting the latest ``develop/`` branch with your PR!

.. _socials: ../socials
