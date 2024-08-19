# Usage (Command Line)

Vineflower can be run from the command line.

```shell
java -jar vineflower.jar [args...] {in}+ [{out}]
```

Vineflower also accepts arguments to change decompilation behavior.

## Arguments

% Several arguments are handled specially in the codebase so they do not have annotations which can be examined on their own

```{option} --cfg={file}, -cfg={file}
  A configuration file containing additional arguments to pass to the decompiler, one per line.
```

```{option} --add-external={library}, -e={library}
  Can be specified as many times as necessary to add library inputs to the decompile classpath
```

The following three options control how decompiled output should be written. If none are specified, the output type will be inferred based on the output. `.zip` or `.jar`-ending outputs will be treated as archives, all other values will be treated as a directory, or no output at all will write the decompiled output to the console.

```{option} --file
  Prefer saving to an archive file.
```

```{option} --folder
  Prefer saving to a folder, potentially extracting an input archive.
```

```{option} --legacy-saving
  Use legacy Fernflower save behavior.
```

It is possible to limit decompiled output to only a certain subset of the input files, for cases where an entire archive is provided as input:

```{option} --only={prefix}, -only={prefix}
Only members whose paths start with `prefix` will be emitted
```

% The rest can be automatically inferred from IFernflowerPreferences

The following values change the decompilation behavior itself.

```{include} generated/usage.md
```
