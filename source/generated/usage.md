```{option} --ascii-strings={bool}, -asc={bool}
Encode non-ASCII characters in string and character literals as Unicode escapes.

**Default**: `0`

```

```{option} --banner={string}, -ban={string}
A message to display at the top of the decompiled file.

```

```{option} --boolean-as-int={bool}, -bto={bool}
Represent integers 0 and 1 as booleans.

**Default**: `1`

```

```{option} --bytecode-source-mapping={bool}, -bsm={bool}
Map Bytecode to source lines.

**Default**: `0`

```

```{option} --decompile-assert={bool}, -das={bool}
Decompile assert statements.

**Default**: `1`

```

```{option} --decompile-complex-constant-dynamic={bool}, -dcc={bool}
Some constant-dynamic expressions can&#39;t be converted to a single Java expression with identical run-time behaviour. This decompiles them to a similar non-lazy expression, marked with a comment.

**Default**: `0`

```

```{option} --decompile-enums={bool}, -den={bool}
Decompile enums.

**Default**: `1`

```

```{option} --decompile-finally={bool}, -fdi={bool}
Decompile finally blocks.

**Default**: `1`

```

```{option} --decompile-generics={bool}, -dgs={bool}
Decompile generics in classes, methods, fields, and variables.

**Default**: `1`

```

```{option} --decompile-inner={bool}, -din={bool}
Process inner classes and add them to the decompiled output.

**Default**: `1`

```

```{option} --decompile-java4={bool}, -dc4={bool}
Resugar the Java 1-4 class reference format instead of leaving the synthetic code.

**Default**: `1`

```

```{option} --decompile-preview={bool}, -dpr={bool}
Decompile features marked as preview or incubating in the latest Java versions.

**Default**: `1`

```

```{option} --decompile-switch-expressions={bool}, -swe={bool}
Decompile switch expressions in modern Java class files.

**Default**: `1`

```

```{option} --decompiler-comments={bool}, -dec={bool}
Sometimes, odd behavior of the bytecode or unfixable problems occur. This enables or disables the adding of those to the decompiled output.

**Default**: `1`

```

```{option} --dump-bytecode-on-error={bool}, -dbe={bool}
Put the bytecode in the method body when an error occurs.

**Default**: `1`

```

```{option} --dump-code-lines={bool}, -dcl={bool}
Dump line mappings to output archive zip entry extra data.

**Default**: `0`

```

```{option} --dump-exception-on-error={bool}, -dee={bool}
Put the exception message in the method body or source file when an error occurs.

**Default**: `1`

```

```{option} --dump-text-tokens={bool}, -dtt={bool}
Dump Text Tokens on each class file

**Default**: `0`

```

```{option} --ensure-synchronized-monitors={bool}, -esm={bool}
If a synchronized block has a monitorenter without any corresponding monitorexit, try to deduce where one should be to ensure the synchronized is correctly decompiled.

**Default**: `1`

```

```{option} --error-message={string}, -erm={string}
Message to display when an error occurs in the decompiler.

**Default**: `Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)`

```

```{option} --explicit-generics={bool}, -ega={bool}
Put explicit diamond generic arguments on method calls.

**Default**: `0`

```

```{option} --force-jsr-inline={bool}, -fji={bool}
Forces the processing of JSR instructions even if the class files shouldn&#39;t contain it (Java 7+)

**Default**: `0`

```

```{option} --hide-default-constructor={bool}, -hdc={bool}
Hide constructors with no parameters and no code.

**Default**: `1`

```

```{option} --hide-empty-super={bool}, -hes={bool}
Hide super() calls with no parameters.

**Default**: `1`

```

```{option} --ignore-invalid-bytecode={bool}, -iib={bool}
Ignore bytecode that is malformed.

**Default**: `0`

```

```{option} --include-classpath={bool}, -iec={bool}
Give the decompiler information about every jar on the classpath.

**Default**: `0`

```

```{option} --include-runtime={string}, -jrt={string}
Give the decompiler information about the Java runtime, either 1 or current for the current runtime, or a path to another runtime

```

```{option} --incorporate-returns={bool}, -ner={bool}
Integrate returns better in try-catch blocks instead of storing them in a temporary variable.

**Default**: `1`

```

```{option} --indent-string={string}, -ind={string}
A string of spaces or tabs that is placed for each indent level.

**Default**: `   `

```

```{option} --inline-simple-lambdas={bool}, -isl={bool}
Remove braces on simple, one line, lambda expressions.

**Default**: `1`

```

```{option} --jad-style-parameter-naming={bool}, -jpr={bool}
Use JAD-style variable naming for parameters.

**Default**: `0`

```

```{option} --jad-style-variable-naming={bool}, -jvn={bool}
Use JAD-style variable naming for local variables, instead of var&lt;index&gt;_&lt;version&gt;.

**Default**: `0`

```

```{option} --keep-literals={bool}, -lit={bool}
Keep NaN, infinities, and pi values as is without resugaring them.

**Default**: `0`

```

```{option} --lambda-to-anonymous-class={bool}, -lac={bool}
Decompile lambda expressions as anonymous classes.

**Default**: `0`

```

```{option} --log-level={string}, -log={string}
Logging level. Must be one of: &#39;info&#39;, &#39;debug&#39;, &#39;warn&#39;, &#39;error&#39;.

**Default**: `INFO`

```

```{option} --max-time-per-method={int}, -mpm={int}
Maximum time in seconds to process a method. This is deprecated, do not use.

**Default**: `0`

```

```{option} --new-line-separator={bool}, -nls={bool}
Use \n instead of \r\n for new lines. Deprecated, do not use.

**Default**: `1`

```

```{option} --override-annotation={bool}, -ovr={bool}
Display override annotations for methods known to the decompiler.

**Default**: `1`

```

```{option} --pattern-matching={bool}, -pam={bool}
Decompile with if and switch pattern matching enabled.

**Default**: `1`

```

```{option} --preferred-line-length={int}, -pll={int}
Max line length before formatting is applied.

**Default**: `160`

```

```{option} --remove-bridge={bool}, -rbr={bool}
Removes any methods that are marked as bridge from the decompiled output.

**Default**: `1`

```

```{option} --remove-empty-try-catch={bool}, -rer={bool}
Remove try-catch blocks with no code.

**Default**: `1`

```

```{option} --remove-getclass={bool}, -rgn={bool}
Remove synthetic getClass() calls created by code such as &#39;obj.new Inner()&#39;.

**Default**: `1`

```

```{option} --remove-imports={bool}, -rim={bool}
Remove import statements from the decompiled code

**Default**: `0`

```

```{option} --remove-synthetic={bool}, -rsy={bool}
Removes any methods and fields that are marked as synthetic from the decompiled output.

**Default**: `1`

```

```{option} --rename-members={bool}, -ren={bool}
Rename classes, fields, and methods with a number suffix to help in deobfuscation.

**Default**: `0`

```

```{option} --show-hidden-statements={bool}, -shs={bool}
Display hidden code blocks for debugging purposes.

**Default**: `0`

```

```{option} --simplify-stack={bool}, -ssp={bool}
Simplify variables across stack bounds to resugar complex statements.

**Default**: `1`

```

```{option} --skip-extra-files={bool}, -sef={bool}
Skip copying non-class files from the input folder or file to the output

**Default**: `0`

```

```{option} --sourcefile-comments={bool}, -sfc={bool}
Add debug comments showing the class SourceFile attribute if present.

**Default**: `0`

```

```{option} --synthetic-not-set={bool}, -nns={bool}
Treat some known structures as synthetic even when not explicitly set.

**Default**: `0`

```

```{option} --ternary-constant-simplification={bool}, -tcs={bool}
Fold branches of ternary expressions that have boolean true and false constants.

**Default**: `0`

```

```{option} --ternary-in-if={bool}, -tco={bool}
Tries to collapse if statements that have a ternary in their condition.

**Default**: `1`

```

```{option} --thread-count={int}, -thr={int}
How many threads to use to decompile.

**Default**: `2`

```

```{option} --try-loop-fix={bool}, -tlf={bool}
Fixes rare cases of malformed decompilation when try blocks are found inside of while loops

**Default**: `1`

```

```{option} --undefined-as-object={bool}, -uto={bool}
Treat nameless types as java.lang.Object.

**Default**: `1`

```

```{option} --use-lvt-names={bool}, -udv={bool}
Use LVT names for local variables and parameters instead of var&lt;index&gt;_&lt;version&gt;.

**Default**: `1`

```

```{option} --use-method-parameters={bool}, -ump={bool}
Use method parameter names, as given in the MethodParameters attribute.

**Default**: `1`

```

```{option} --user-renamer-class={string}, -urc={string}
Path to a class that implements IIdentifierRenamer.

```

```{option} --verify-anonymous-classes={bool}, -vac={bool}
Verify that anonymous classes are local.

**Default**: `0`

```

```{option} --verify-merges={bool}, -vvm={bool}
Tries harder to verify the validity of variable merges. If there are strange variable recompilation issues, this is a good place to start.

**Default**: `0`

```

```{option} --warn-inconsistent-inner-attributes={bool}, -win={bool}
Warn about inconsistent inner class attributes

**Default**: `1`

```

