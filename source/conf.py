# Configuration file for the Sphinx documentation builder.
#
# This file only contains a selection of the most common options. For a full
# list see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

import os
import sys

# -- Path setup --------------------------------------------------------------

# If extensions (or modules to document with autodoc) are in another directory,
# add these directories to sys.path here. If the directory is relative to the
# documentation root, use os.path.abspath to make it absolute, like shown here.
#
# import os
# import sys
# sys.path.insert(0, os.path.abspath('.'))


# -- Project information -----------------------------------------------------
from pathlib import Path

project = 'Vineflower'
copyright = '2023 Vineflower Team and Contributors'
author = 'Vineflower team'

# The short X.Y versions
# The full api version, including alpha/beta/rc tags
release = '1.9.3'

rst_prolog = f"""
.. |version| replace:: {release}
"""

myst_substitutions = {
    'version': release
}

if release.endswith('-SNAPSHOT'):
    tags.add('draft')

if 'GITHUB_REF' in os.environ:
    ref = os.environ['GITHUB_REF']
    if ref.startswith("refs/pull/"):
        pr_number = ref[len("refs/pull/"):-len("/merge")]
        rst_prolog += f"""
.. caution::

    This version of the Vineflower site has been built as a preview of pull request :github:`vineflower-site#{pr_number}`, and has not been reviewed.

    Please consult the pull request to view any discussion and existing reviews.
"""
    else:
        html_baseurl = "https://vineflower.org/"

# -- General configuration ---------------------------------------------------

# Add local extensions
sys.path.append(os.path.abspath("./_ext"))

# Add any Sphinx extension module names here, as strings. They can be
# extensions coming with Sphinx (named 'sphinx.ext.*') or your custom
# ones.
extensions = [
  'myst_parser',
  'sphinx_copybutton',
  'sphinx_design',
  'sphinx_github_changelog',
  'sphinx_github_role',
  'sphinx_reredirects',
  'sphinx_substitution_extensions',
  'sphinxcontrib.spelling',
  'vineflower_site_extensions'
]

# Add any paths that contain templates here, relative to this directory.
# templates_path = ['_templates']

# List of patterns, relative to source directory, that match files and
# directories to ignore when looking for source files.
# This pattern also affects html_static_path and html_extra_path.
exclude_patterns = ['_ext']


# General style

smartquotes = True
language = 'en'

pygments_style = 'friendly'
pygments_dark_style = 'dracula'

myst_enable_extensions=[
  "colon_fence",
  "deflist",
  "fieldlist",
  "dollarmath",
  "html_admonition",
  "replacements",
  "smartquotes",
  "tasklist"
]


# -- Options for HTML output -------------------------------------------------

# The theme to use for HTML and HTML Help pages.  See the documentation for
# a list of builtin themes.
#
html_theme = 'furo'

# Add any paths that contain custom static files (such as style sheets) here,
# relative to this directory. They are copied after the builtin static files,
# so a file named "default.css" will overwrite the builtin "default.css".
html_static_path = ['_static']
html_css_files = ["button/button.css"]

html_theme_options = {
    "light_logo": "logo-light.png",
    "dark_logo": "logo-dark.png",
    'light_css_variables': {
        "color-brand-primary": "#0F1F35",
        "color-brand-content": "#C24DFF",
    },
    'dark_css_variables': {
        "color-brand-primary": "#B8FF5C",
        "color-brand-content": "#ABA9FF",
    },
    'sidebar_hide_name': True
}

html_title = f'Vineflower (v{release})'
html_show_sourcelink = False
html_copy_source = False

html_favicon = '_static/favicon.ico'

# sphinx-github-role
github_default_org_project = ('Vineflower', 'vineflower')

# sphinx-reredirects
redirects = {
    'socials/discord': 'https://discord.com/invite/MsBAZamXGF',
    'socials/matrix': 'https://matrix.to/#/#vineflower:dfsek.com'
}

# sphinxcontrib-spelling
spelling_word_list_filename='../.config/spelling_wordlist.txt'
spelling_show_suggestions=True
spelling_suggestion_limit=5
