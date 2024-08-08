Contributing to the Site
========================

.. note::
    This document provides information on contributing to the Vineflower site. For more information
    on contributing to Vineflower or its supporting projects, check their READMEs.

Thank you for your interest in contributing to the Vineflower site. We use `Sphinx`_ to build
the documentation, along with several extensions. This page aims to be a quick rundown of what is needed to
build the documentation, and some useful syntax.


Building
--------

Sphinx is a Python tool, so the steps to build this documentation will be familiar to anyone who's set up a Python project before.

.. note::

    For those wishing to make a simple contribution quickly, we provide a GitHub Codespaces configuration file for this repository
    allowing for quick setup with VS Code as an editor.

Make sure `Git <https://git-scm.com>`_ and `Python <https://www.python.org>`_ 3.11 or newer are installed.
These instructions assume you are working from a terminal, either on Windows or Linux.

.. tab-set::

    .. tab-item:: Linux/macOS (POSIX)

        1. Clone the repository from `GitHub <https://github.com/Vineflower/vineflower-site>`_ and switch into the directory
        2. Install pipenv (if not present): ``$ apt install pipenv``
        3. Install the dependencies: ``pipenv install``
        4. Generate the documentation contents from the current Vineflower release: ``pushd docgen/ && ./gradlew generate --target-dir ../source/generated/ && popd``
        5. Build the documentation: ``pipenv run make livehtml``
        6. Open a browser to ``https://localhost:8000`` to view the just-built site. Pages will auto-refresh when changes are made.

    .. tab-item:: Windows (PowerShell)

        1. Clone the repository from `GitHub <https://github.com/Vineflower/vineflower-site/>`_ and switch into the directory
        2. Install pipenv (if not present): ``pip install pipenv```
        3. Install the dependencies: ``pipenv install``
        4. Generate the documentation contents from the current Vineflower release: ``cd docgen/; ./gradlew generate --target-dir ../source/generated/; cd -``
        5. Build the documentation: ``pipenv run ./make livehtml``
        6. Open a browser to ``https://localhost:8000`` to view the just-built site. Pages will auto-refresh when changes are made.


Any text editor will work for editing the documentation, but we've had the best experience with Visual Studio Code or vim, each of which have mature reST and markdown plugins.
A typical development environment has a text editor and web browser side-by-side, with the web browser viewing the locally served test site.

Once you've written changes, they can be submitted for inclusion in the docs with a GitHub Pull Request.

Style
-----

The Vineflowe site is primarily written in English. We attempt to mostly follow American spellings, though that can often be inconsistent. There is a basic spellchecker
that will be run on PRs, or which can be run locally with the ``pipenv run make spelling`` command. We also recommend using the LanguageTool VS Code extension for in-editor suggestions.
For technical language that should always be accepted, custom words can be added in the :file:`.config/spelling_wordlist.txt` file. For one-off exceptions (such as project names),
use the :samp:`:spelling:ignore:\`{word}\``

All code samples should be given in Java.

When providing examples for build tools, those examples should be provided for Maven, Gradle with Groovy buildscript, and Gradle with Kotlin buildscript.

Helpful Roles and Directives
----------------------------

Source for this documentation is primarily in the `reStructured Text`_ format, the default used by Sphinx. The syntax may be a touch different from what many people are used to,
but the documentation for reST should give a good overview. There are still many included directives and roles, especially once Sphinx's extensions enter the
mix, so here are some of the less common ones.

Source for this documentation is primarily in MyST flavored markdown. We use the `MyST Parser`_ package to support Markdown with several Sphinx-specific extensions to support roles and directives.
See `their documentation <https://myst-parser.readthedocs.io/en/latest/syntax/syntax.html>`_ for more information on common directives and roles.

Standard Sphinx/Docutils
^^^^^^^^^^^^^^^^^^^^^^^^

See the Sphinx documentation on `Roles <https://www.sphinx-doc.org/en/master/usage/restructuredtext/roles.html>`_ and `Directives <https://www.sphinx-doc.org/en/master/usage/restructuredtext/directives.html>`_ for a full listing.

Plugins we use
^^^^^^^^^^^^^^

We take advantage of some of the many Sphinx plugins available to add helpful tools to the documentation site.

`sphinx-design <https://sphinx-design.readthedocs.io/en/latest/>`_
    Many useful directives for page layout assistance, and tab views for displaying alternative versions of syntax side by side.
`Sphinx-Substitution-Extensions <https://pypi.org/project/Sphinx-Substitution-Extensions/>`_
    Allows substitution within code blocks, used for build setup instructions.
`sphinx-github-changelog <https://sphinx-github-changelog.readthedocs.io/en/latest/>`_
    This plugin will likely not be interesting to most contributors, but it powers the :doc:`changelogs <version-history>` included for Vineflower
`sphinx-reredirects <https://documatt.gitlab.io/sphinx-reredirects/>`_
    If it makes sense to change the URL of a documentation page, this plugin allows inserting redirects from the old page to the new one.


Custom for this documentation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

While we try to rely on external projects as much as possible, there are some small features that are specific to the Vineflower site.

.. rst:role:: java

    The ``:java:`` (or ``{java}`` in Markdown) role will insert its contents as an inline syntax-highlighted code block.

    For example, ``:java:`Component.text("Hello world", NamedTextColor.RED)``` will produce :java:`Component.text("Hello world", NamedTextColor.RED)`

.. _Sphinx: https://www.sphinx-doc.org/
.. _reStructured Text: https://docutils.sourceforge.io/rst.html
.. _MyST Parser: https://myst-parser.readthedocs.io/en/latest
