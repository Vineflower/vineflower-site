#!/usr/bin/env python3
# This file is part of adventure-docs, licensed under the MIT License.
#
# Copyright (c) 2023 Vineflower Contributors
# Copyright (c) 2017-2022 KyoriPowered
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

import re

from sphinx.application import Sphinx
from sphinx.builders.dirhtml import DirectoryHTMLBuilder
from sphinx.roles import code_role
from docutils.parsers.rst import nodes

def setup(app: Sphinx):
    app.add_role("mojira", mojira_role)
    app.add_role("java", highlight_role("java"))
    app.connect("html-page-context", canonical_url)

_issue_regex = re.compile(r'[A-Z]+-[1-9][0-9]*')
_mojira_url = "https://bugs.mojang.com/browse/"


def mojira_role(role, rawtext, text, lineno, inliner, options={}, content=[]):
    if not _issue_regex.fullmatch(text):
        msg = inliner.reporter.error(f'Issue number must be in the format PROJECT-1234, but {text} was given instead', line=lineno)
        prb = inliner.problematic(rawtext, rawtext, msg)
        return [prb], [msg]

    ref = _mojira_url + text

    node = nodes.reference(rawtext, text, refuri=ref, **options)

    return [node], []

def highlight_role(language: str):
    def lang_highlight_role(role, rawtext, text, lineno, inliner, options={}, content=[]):
        extended_options: dict = options.copy()
        extended_options["language"] = language
        extended_options["class"] = "highlight"

        return code_role(role, rawtext, text, lineno, inliner, options=extended_options, content=content)

    return lang_highlight_role

def canonical_url(app: Sphinx, pagename, templatename, context, doctree):
    """Sphinx 1.8 builds a canonical URL if ``html_baseurl`` config is
    set. However, it builds a URL ending with ".html" when using the
    dirhtml builder, which is incorrect. Detect this and generate the
    correct URL for each page.

    Shamelessly ripped from https://github.com/pallets/pallets-sphinx-themes/blob/7a6fa47323af403c19008a5df8b5302a1ead7be3/src/pallets_sphinx_themes/__init__.py#L72C1-L91C39

    Copyright 2007 Pallets `License <https://github.com/pallets/pallets-sphinx-themes/blob/7a6fa47323af403c19008a5df8b5302a1ead7be3/LICENSE.rst>`_
    """
    base = app.config.html_baseurl

    if (
        not base
        or not isinstance(app.builder, DirectoryHTMLBuilder)
        or not context["pageurl"]
        or not context["pageurl"].endswith(".html")
    ):
        return

    # Fix pageurl for dirhtml builder if this version of Sphinx still
    # generates .html URLs.
    target = app.builder.get_target_uri(pagename)
    context["pageurl"] = base + target

