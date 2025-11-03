=================
Output Comparison
=================

This page shows some examples of Vineflower's output when compared to other contemporary decompilers: Fernflower, CFR, Procyon, and JD-GUI.

This page was created using Vineflower `1.11.1 <https://github.com/Vineflower/vineflower/releases/tag/1.11.1>`__, Fernflower built from commit `7261c6b73b82e7a0d5d82d4bb179cc090df91bb5 <https://github.com/JetBrains/fernflower/commit/7261c6b73b82e7a0d5d82d4bb179cc090df91bb5>`__, and CFR `0.152 <https://github.com/leibnitz27/cfr/releases/tag/0.152>`__, Procyon `0.6.0 <https://github.com/mstrobel/procyon/releases/tag/v0.6.0>`__, and JD-GUI `1.6.6 <https://github.com/java-decompiler/jd-gui/releases/tag/v1.6.6>`__. Some classes that didn't open in JD-GUI were tried again in JD-CLI `1.2.0 <https://github.com/intoolswetrust/jd-cli/releases/tag/jd-cli-1.2.0>`__ to give it another shot. Fernflower was run with dgs=1 (decompile generics), crp=1 (decompile record patterns), cps=1 (decompile switch patterns) iec=1 (include entire classpath) to match Vineflower's defaults. CFR was run with --sealed true. The output code style has been normalized to 2 spaces.

Double casted float precision
-----------------------------

This test shows implicit casting from a float constant to double. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestFloatPrecision.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test() {
          double x = 0.2F;
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test() {
          double x = 0.2F;
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test() {
          double x = (double)0.2F;
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test() {
          double x = 0.2f;
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test() {
          final double x = 0.20000000298023224;
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void test() {
          double x = 0.20000000298023224D;
        }

Vineflower, Fernflower, and CFR are able to write the constant as a float instead of a rounded double. Procyon and JD-GUI are unable to recognize the pattern. Fernflower also emits an unnecessary :java:`(float)` cast.

Shift left on long
------------------

This simple test exposes a bug in Fernflower's statement resugaring. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestShiftLoop.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public static void test(long[] l) {
          long x;

          x = l[0];
          for (int i = 1; i < 2; i++) {
            x <<= 3;
          }

          x = l[4];
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public static void test(long[] l) {
          long x = l[0];

          for (int i = 1; i < 2; i++) {
            x <<= 3;
          }

          x = l[4];
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public static void test(long[] l) {
          // $FF: Couldn't be decompiled
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public static void test(long[] l) {
          long x = l[0];
          for (int i2 = 1; i2 < 2; ++i2) {
            x <<= 3;
          }
          x = l[4];
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public static void test(final long[] l) {
          long x = l[0];
          for (int i = 1; i < 2; ++i) {
            x <<= 3;
          }
          x = l[4];
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public static void test(long[] l) {
          long x = l[0];
          for (int i = 1; i < 2; i++)
            x <<= 3L;
          x = l[4];
        }

Fernflower fails with an exception, while all others are able to decompile it.

Try-with-resources with finally
-------------------------------

This test shows a try-with-resources block used with a finally. The bytecode emitted here is from Java 16. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java16/pkg/TestTryWithResourcesFinallyJ16.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test(File file) throws FileNotFoundException {
          try (Scanner scanner = new Scanner(file)) {
            scanner.next();
          } finally {
            System.out.println("Hello");
          }
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test(File file) throws FileNotFoundException {
          try (Scanner scanner = new Scanner(file)) {
            scanner.next();
          } finally {
            System.out.println("Hello");
          }
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test(File file) throws FileNotFoundException {
          try {
            Scanner scanner = new Scanner(file);

            try {
              scanner.next();
            } catch (Throwable var10) {
              try {
                scanner.close();
              } catch (Throwable var9) {
                var10.addSuppressed(var9);
              }

              throw var10;
            }
            scanner.close();
          } finally {
            System.out.println("Hello");
          }
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test(File file) throws FileNotFoundException {
          try (Scanner scanner = new Scanner(file);){
            scanner.next();
          }
          finally {
            System.out.println("Hello");
          }
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test(final File file) throws FileNotFoundException {
          try (final Scanner scanner = new Scanner(file)) {
            scanner.next();
          }
          finally {
            System.out.println("Hello");
          }
        }


   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void test(File file) throws FileNotFoundException {
          try {
            Scanner scanner = new Scanner(file);
            try {
              scanner.next();
              scanner.close();
            } catch (Throwable throwable) {
              try {
                scanner.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }
              throw throwable;
            }
          } finally {
            System.out.println("Hello");
          }
        }

Vineflower, CFR, and Procyon can decompile it properly, while Fernflower and JD-GUI don't recognize the pattern.


Local enums
-----------

Local enums are a Java 16 feature. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java16/pkg/TestLocalEnum.java


.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          enum Type {
            VALID,
            INVALID
          }

          Type type = i == 0 ? Type.VALID : Type.INVALID;
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          enum Type {
            VALID,
            INVALID;
          }

          Type type = i == 0 ? Type.VALID : Type.INVALID;
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          final class Type extends Enum<Type> {
            VALID,
            INVALID;

            // $FF: synthetic method
            private static Type[] $values() {
              return new Type[]{VALID, INVALID};
            }
          }

          Type type = i == 0 ? Type.VALID : Type.INVALID;
        }


   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          static enum Type {
            VALID,
            INVALID;

          }
          Type type = i == 0 ? Type.VALID : Type.INVALID;
        }


   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test(final int i) {
          enum Type
          {
            VALID,
            INVALID;
          }
          final Type type = (i == 0) ? Type.VALID : Type.INVALID;
        }


   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          Type type = (i == 0) ? Type.VALID : Type.INVALID;
        }


Vineflower and Procyon are able to recover the local enum, while Fernflower is unable to recognize it as an enum. CFR also recovers the enum, but it adds a static keyword which is illegal in the local context. JD-GUI omits it entirely.


While loop with break
---------------------

This test shows how a loop with two different exits can be decompiled by different decompilers. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestLoopBreak.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          while (i > 10) {
            i++;

            if (i == 15) {
              break;
            }

            System.out.println(0);
          }
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          while (i > 10) {
            if (++i != 15) {
              System.out.println(0);
              continue;
            }
            break;
          }
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          while(true) {
            if (i > 10) {
              ++i;
              if (i != 15) {
                 System.out.println((int)0);
                 continue;
              }
            }

            return;
          }
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          while (i > 10 && ++i != 15) {
            System.out.println(0);
          }
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          while (i > 10 && ++i != 15) {
            System.out.println(0);
          }
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void test(int i) {
          i++;
          while (i > 10 && i != 15)
            System.out.println(0);
        }


Vineflower decompiles the loop into a :java:`while` loop, while Fernflower is unable to turn the :java:`while (true)` loop into a :java:`while (condition)` loop. Fernflower also produces a useless (int) cast for println. CFR and Procyon manage to produce the most concise code out of the bunch, even more concise than the source. JD-GUI's code is invalid, as the :java:`++i` is pulled out of the condition.


Super wildcard synthetic cast
-----------------------------

This test shows a cast that is required for code correctness, but isn't stored in the classfile. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestGenericSuperCast.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public <T> Class<T> test(Inner<T> inner) {
          Class<T> t = (Class<T>) inner.get();
          return (Class<T>) inner.get();
        }

        // Not shown in decompiled output for space
        public class Inner<T> {
          public Class<? super T> get() {
            return null;
          }
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public <T> Class<T> test(TestGenericSuperCast.Inner<T> inner) {
          Class<T> t = (Class<T>)inner.get();
          return (Class<T>)inner.get();
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public <T> Class<T> test(Inner<T> inner) {
          Class<T> t = inner.get();
          return inner.get();
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public <T> Class<T> test(Inner<T> inner) {
          Class<T> t = inner.get();
          return inner.get();
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public <T> Class<T> test(final Inner<T> inner) {
          final Class<T> t = (Class<T>)inner.get();
          return (Class<T>)inner.get();
        }


   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public <T> Class<T> test(Inner<T> inner) {
          Class<T> t = inner.get();
          return inner.get();
        }

Vineflower and Procyon properly add the cast back in while the others don't, leading to incorrect code.

Switch expressions
------------------

Switch expressions are a Java 14 feature. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java16/pkg/TestReturnSwitchExpression1.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public String test(int i) {
          return switch (i) {
            case 1 -> "1";
            case 2 -> "2";
            default -> "Unknown";
          };
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public String test(int i) {
          return switch (i) {
            case 1 -> "1";
            case 2 -> "2";
            default -> "Unknown";
          };
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public String test(int i) {
          String var10000;
          switch (i) {
            case 1 -> var10000 = "1";
            case 2 -> var10000 = "2";
            default -> var10000 = "Unknown";
          }

          return var10000;
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public String test(int i) {
          return switch (i) {
            case 1 -> "1";
            case 2 -> "2";
            default -> "Unknown";
          };
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public String test(final int i) {
          return switch (i) {
            case 1 -> "1";
            case 2 -> "2";
            default -> "Unknown";
          };
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public String test(int i) {
          switch (i) {
            case 1:

            case 2:

          }
          return


            "Unknown";
        }

Vineflower, CFR, and Procyon properly decompile the switch expression. Vineflower creates a switch expression, but is unable to process the assignment as an implicit :java:`yield` expression. JD-GUI creates an invalid decompilation.

Generic usage of null
---------------------

This test shows the usage of null as an argument to a generic method. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestGenericNull.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public Object doThing(Map<Integer, Optional<T>> map) {
          return map.get(0).orElse(null);
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public Object doThing(Map<Integer, Optional<T>> map) {
          return map.get(0).orElse(null);
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public Object doThing(Map<Integer, Optional<T>> map) {
          return (map.get(0)).orElse((Object)null);
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public Object doThing(Map<Integer, Optional<T>> map) {
          return map.get(0).orElse(null);
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public Object doThing(final Map<Integer, Optional<T>> map) {
          return map.get(0).orElse((Object)null);
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public Object doThing(Map<Integer, Optional<T>> map) {
          return ((Optional)map.get(Integer.valueOf(0))).orElse(null);
        }

Vineflower and CFR decompile the null properly, while Fernflower and Procyon create an unnecesssary :java:`(Object)` cast. JD-GUI fails to unbox the integer and adds an :java:`(Optional)` cast.

Pattern matching in argument
----------------------------

This test shows the usage of pattern matching in an argument to produce a boolean. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java16/pkg/TestPatternMatchingInline.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test(Object o) {
          accept(o, o instanceof String s && s.length() > 5);
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test(Object o) {
          this.accept(o, o instanceof String s && s.length() > 5);
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test(Object o) {
          boolean var10002;
          label12: {
            if (o instanceof String s) {
              if (s.length() > 5) {
                var10002 = true;
                break label12;
              }
            }

            var10002 = false;
          }

          this.accept(o, var10002);
        }


   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test(Object o) {
          String s;
          Object object = o;
          this.accept(o, object instanceof String && (s = (String)object).length() > 5);
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test(final Object o) {
          boolean b = false;
          Label_0029: {
            if (o instanceof final String s) {
              if (s.length() > 5) {
                b = true;
                break Label_0029;
              }
            }
            b = false;
          }
          this.accept(o, b);
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        <failed to decompile>

Vineflower is able to recover the pattern match. CFR is able to recover the pattern match, but it produces unnecessary local variable definitions. Fernflower and Procyon are unable to recover the pattern match, and use a labeled break to recover the control flow. JD-GUI can't decompile the method.

Top-level boxing
----------------

This test shows the usage of boxing as a top-level statement, which can confuse decompilers that do eager unboxing. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestDanglingBoxingCall.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test(int x) {
          if ((x ^ 126) == 7) {
            Integer.valueOf(0xFFFF);
          } else {
            Boolean.valueOf(false);
          }

          Float.valueOf(0.9f);
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test(int x) {
          if ((x ^ 126) == 7) {
            Integer.valueOf(65535);
          } else {
            Boolean.valueOf(false);
          }

          Float.valueOf(0.9F);
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test(int x) {
          if ((x ^ 126) == 7) {
            65535;
          } else {
            false;
          }

          0.9F;
        }


   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test(int x) {
          if ((x ^ 0x7E) == 7) {
            Integer.valueOf(65535);
          } else {
            Boolean.valueOf(false);
          }
          Float.valueOf(0.9f);
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test(final int x) {
          if ((x ^ 0x7E) == 0x7) {
            65535;
          }
          else {
            false;
          }
          0.9f;
        }


   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void test(int x) {
          if ((x ^ 0x7E) == 7) {
            Integer.valueOf(65535);
          } else {
            Boolean.valueOf(false);
          }
          Float.valueOf(0.9F);
        }

Vineflower, CFR, and JD-GUI properly keep the boxing. Fernflower and Procyon unbox the

Double-brace initializers in enums
----------------------------------

This test contains double brace initializers in enums, in compiled with Java 17. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java17/pkg/TestDoubleBraceInitializersJ17.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public enum TestEnum {
          A {{
            System.out.println("A");
          }},
          B {{
            System.out.println("B");
          }}
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public static enum TestEnum {
          A {
            {
              System.out.println("A");
            }
          },
          B {
            {
              System.out.println("B");
            }
          };
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public static enum TestEnum {
          A {
            private {
              System.out.println("A");
            }
          },
          B {
            private {
              System.out.println("B");
            }
          };
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public static enum TestEnum {
          A{
            {
              System.out.println("A");
            }
          }
          ,
          B{
            {
              System.out.println("B");
            }
          };
        }


   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public enum TestEnum permits TestEnum$1, TestEnum$2
        {
          A {
            {
              System.out.println("A");
            }
          },
          B {
            {
              System.out.println("B");
            }
          };
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        <no decompiled output>

Vineflower, CFR, and Procyon can decompile the code properly. Fernflower adds an unnecessary :java:`private` modifier, while JD-GUI doesn't produce any output.

Switch pattern matching
-----------------------

Switch pattern matching is a Java 21 feature. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java21/pkg/TestSwitchPatternMatchingJ21.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public String test2(Object o) {
          return switch (o) {
            case Integer i -> Integer.toString(i);
            case String s -> s;
            default -> "null";
          };
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:


        public String test2(Object o) {
          return switch (o) {
            case Integer i -> Integer.toString(i);
            case String s -> s;
            default -> "null";
          };
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public String test2(Object o) {
          String var10000;
          switch (o) {
             case Integer i -> var10000 = Integer.toString(i);
             case String s -> var10000 = s;
             default -> var10000 = "null";
          }

          return var10000;
       }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public String test2(Object o) {
          Object object = o;
          Objects.requireNonNull(object);
          Object object2 = object;
          int n = 0;
          return switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{Integer.class, String.class}, (Object)object2, n)) {
            case 0 -> {
              Integer i = (Integer)object2;
              yield Integer.toString(i);
            }
            case 1 -> {
              String s;
              yield s = (String)object2;
            }
            default -> "null";
          };
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public String test2(final Object o) {
          Objects.requireNonNull(o);
          String string = null;
          switch (/* invokedynamic(!) */ProcyonInvokeDynamicHelper_2.invoke(o, false)) {
            case 0: {
              final Integer i = (Integer)o;
              string = Integer.toString(i);
              break;
            }
            case 1: {
              final String s = string = (String)o;
              break;
            }
            default: {
              string = "null";
              break;
            }
          }
          return string;
        }


   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        <decompilation failure>

Vineflower is able to fully recover the pattern match. Fernflower can detect the pattern match, but is unable to turn it into a switch expression. CFR and Procyon can't recognize the pattern match and end up leaving in the invokedynamic call. JD-GUI is unable to decompile the method.

Definite Assignment
-------------------

This test is a gauntlet of different definite assignment rules in a method. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java17/pkg/TestDefiniteAssignment.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        void testAssignments(int n, boolean bool) {
          int a;
          if (bool && ((a = n) > 0 || (a = -n) > 100)) {
            System.out.println(a);
          }

          int b;
          if (bool || (b = (b = n) * b) > 0) {
            System.out.println("b");
          } else {
            System.out.println(b);
          }

          {
            double cFake = 0.01;
            System.out.println(cFake);
          }

          double c;
          if (!((n < 1.0 - n) && (c = (n + 5)) > (c * c - c / 2)) ? n < 5.0 - (c = n) : n > c) {
            System.out.println(c);
            c += 2;
          } else {
            c += 5;
          }
          System.out.println(c);

          boolean x;
          double d;
          if (x = ((d = n) > 0)) {
            System.out.println(d);
          }

        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        void testAssignments(int n, boolean bool) {
          if (bool) {
            int a = n;
            if (n > 0 || (a = -n) > 100) {
              System.out.println(a);
            }
          }

          int b;
          if (!bool && (b = n * n) <= 0) {
            System.out.println(b);
          } else {
            System.out.println("b");
          }

          double cFake = 0.01;
          System.out.println(cFake);
          if (n < 1.0 - n && (cFake = n + 5) > cFake * cFake - cFake / 2.0 ? !(n > cFake) : !(n < 5.0 - (cFake = n))) {
            cFake += 5.0;
          } else {
            System.out.println(cFake);
            cFake += 2.0;
          }

          System.out.println(cFake);
          double d;
          if ((d = n) > 0.0) {
            System.out.println(d);
          }
        }


   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        void testAssignments(int n, boolean bool) {
          int a;
          if (bool && (n > 0 || (a = -n) > 100)) {
            System.out.println(a);
          }

          int b;
          if (!bool && (b = n * n) <= 0) {
            System.out.println(b);
          } else {
            System.out.println("b");
          }

          double cFake;
          label38: {
            label55: {
              cFake = 0.01;
              System.out.println(cFake);
              if ((double)n < (double)1.0F - (double)n && (cFake = (double)(n + 5)) > cFake * cFake - cFake / (double)2.0F) {
                if ((double)n > cFake) {
                  break label55;
                }
              } else if ((double)n < (double)5.0F - (cFake = (double)n)) {
                  break label55;
              }

              cFake += (double)5.0F;
              break label38;
            }

            System.out.println(cFake);
            cFake += (double)2.0F;
          }

          System.out.println(cFake);
          double d;
          if ((d = (double)n) > (double)0.0F) {
            System.out.println(d);
          }

        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        /*
         * Unable to fully structure code
         */
        void testAssignments(int n, boolean bool) {
          block6: {
            if (bool && ((a = n) > 0 || (a = -n) > 100)) {
              System.out.println(a);
            }
            if (bool) ** GOTO lbl-1000
              b = n;
              if ((b *= b) > 0) lbl-1000:
              // 2 sources

              {
                System.out.println("b");
              } else {
                System.out.println(b);
              }
              cFake = 0.01;
              System.out.println(cFake);
              if ((double)n < 1.0 - (double)n && (c = (double)(n + 5)) > c * c - c / 2.0) break block6;
              c = n;
              if (!((double)n < 5.0 - v0)) ** GOTO lbl-1000
              ** GOTO lbl-1000
          }
          if ((double)n > c) lbl-1000:
          // 2 sources

          {
            System.out.println(c);
            c += 2.0;
          } else lbl-1000:
            // 2 sources

          {
            c += 5.0;
          }
          System.out.println(c);
          d = n;
          x = v1 > 0.0;
          if (x) {
            System.out.println(d);
          }
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        void testAssignments(final int n, final boolean bool) {
          if (bool) {
            int a = n;
            if (n > 0 || (a = -n) > 100) {
              System.out.println(a);
            }
          }
          Label_0062: {
            if (!bool) {
              int b = n;
              if ((b *= n) <= 0) {
                System.out.println(b);
                break Label_0062;
              }
            }
            System.out.println("b");
          }
          final double cFake = 0.01;
          System.out.println(cFake);
          double c = 0.0;
          Label_0161: {
            Label_0153: {
              if (n >= 1.0 - n || (c = n + 5) <= c * c - c / 2.0) {
                if (n >= 5.0 - (c = n)) {
                  break Label_0153;
                }
              }
              else if (n <= c) {
                break Label_0153;
              }
              System.out.println(c);
              c += 2.0;
              break Label_0161;
            }
            c += 5.0;
          }
          System.out.println(c);
          final double d;
          final boolean x;
          if (x = ((d = n) > 0.0)) {
            System.out.println(d);
          }
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        void testAssignments(int n, boolean bool) {
          int a;
          if (bool && ((a = n) > 0 || (a = -n) > 100))
            System.out.println(a);
          int b;
          if (bool || (b = (b = n) * b) > 0) {
            System.out.println("b");
          } else {
            System.out.println(b);
          }
          double cFake = 0.01D;
          System.out.println(cFake);
          double c;
          if (((c = (n + 5)) <= c * c - c / 2.0D) ? (n < 5.0D - (c = n)) : (n > c)) {
            System.out.println(c);
            c += 2.0D;
          } else {
            c += 5.0D;
          }
          System.out.println(c);
          boolean x;
          double d;
          if (x = ((d = n) > 0.0D))
            System.out.println(d);
        }

Vineflower is able to process the definite assignments rules for the most part, but it omits an unncessary :java:`x =` in the last if statement and doesn't create a boolean and in the first. It also mistakenly combines multiple LVT entries, leading to the wrong name being used for :java:`c`. Fernflower and Procyon need to use labels to construct the proper control flow for the statement. CFR can't recover the control flow at all, and ends up producing goto statements to show the control flow. JD-GUI can decompile without labels, but it omits the :java:`!((n < 1.0 - n)` condition, changing the meaning of the code.

Loop in switch with labeled break
---------------------------------

This test shows confusing control flow with a loop with a labeled break inside a switch. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java8/pkg/TestSwitchLoop.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test8(int i) {
          switch (i) {
            case 0:
              label: {
                for (int j = 0; j < 10; j++) {
                  if (j == 3) {
                    break label;
                  }
                }

                System.out.println(0);
              }
              System.out.println("after");
            case 1:
              System.out.println(1);
          }

          System.out.println("after2");
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test8(int i) {
          switch (i) {
            case 0:
              int j = 0;

              while (true) {
                if (j >= 10) {
                  System.out.println(0);
                  break;
                }

                if (j == 3) {
                  break;
                }

                j++;
              }

              System.out.println("after");
            case 1:
              System.out.println(1);
          }

          System.out.println("after2");
        }



   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test8(int i) {
          switch (i) {
            case 0:
              int j = 0;

              while(true) {
                if (j >= 10) {
                  System.out.println((int)0);
                  break;
                }

                if (j == 3) {
                  break;
                }

                ++j;
              }

              System.out.println("after");
            case 1:
              System.out.println((int)1);
          }

          System.out.println("after2");
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        /*
         * Unable to fully structure code
         */
        public void test8(int i) {
          switch (i) {
            case 0: {
              for (j = 0; j < 10; ++j) {
                if (j != 3) {
                  continue;
                }
                ** GOTO lbl8
              }
              System.out.println(0);
        lbl8:
              // 2 sources

              System.out.println("after");
            }
            case 1: {
              System.out.println(1);
            }
          }
          System.out.println("after2");
        }


   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test8(final int i) {
          switch (i) {
            case 0: {
              while (true) {
                for (int j = 0; j < 10; ++j) {
                  if (j == 3) {
                    System.out.println("after");
                  }
                }
                System.out.println(0);
                continue;
              }
            }
            case 1: {
              System.out.println(1);
              break;
            }
          }
          System.out.println("after2");
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        <decompilation failure>

Vineflower and Fernflower properly decompile the loop, but they don't recover the for loop. Procyon recovers the for loop but keeps the while loop, creating incorrect control flow due to an infinite loop. CFR is unable to recover the control flow, and has to approximate using goto. JD-GUI fails to decompile the class.

Nameless local class
--------------------

This test shows the example of nameless local classes, which require the usage of :java:`var` to represent as a local variable. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java16/pkg/TestAnonymousClassJ16.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void testNamelessTypeVirtual() {
          var printer = new Object() {
            void println(String s) {
              System.out.println(s);
            }
          };
          printer.println("goodbye, world!");
        }

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void testNamelessTypeVirtual() {
          var printer = new Object() {
            void println(String s) {
              System.out.println(s);
            }
          };
          printer.println("goodbye, world!");
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void testNamelessTypeVirtual() {
          <undefinedtype> printer = (<undefinedtype>)(new Object() {
            void println(String s) {
              System.out.println(s);
            }
          });
          printer.println("goodbye, world!");
        }

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void testNamelessTypeVirtual() {
          var printer = new Object(){

            void println(String s) {
              System.out.println(s);
            }
          };
          printer.println("goodbye, world!");
        }


   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void testNamelessTypeVirtual() {
          final Object printer = new Object() {
            void println(final String s) {
              System.out.println(s);
            }
          };
          printer.println("goodbye, world!");
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void testNamelessTypeVirtual() {
          Object object = new Object(this);
          object.println("goodbye, world!");
        }

Vineflower and CFR are able to create the proper variable definition with :java:`var`, while Procyon and JD-GUI create an invalid :java:`Object` variable. JD-GUI also doesn't show the decompiled inner class. Fernflower fails with an undefinable type.

Text blocks
-----------

Text blocks are a Java 15 feature that compile into the same bytecode as regular strings. https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java16/pkg/TestTextBlocks.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        private final String text = """
          Hello!
          This is a text block!
          It's multiple lines long.
          I can use "quotes" in it.
          It's rather cool.
          """;

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        private final String text = "Hello!\nThis is a text block!\nIt's multiple lines long.\nI can use \"quotes\" in it.\nIt's rather cool.\n";

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        private final String text = "Hello!\nThis is a text block!\nIt's multiple lines long.\nI can use \"quotes\" in it.\nIt's rather cool.\n";

   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        private final String text = "Hello!\nThis is a text block!\nIt's multiple lines long.\nI can use \"quotes\" in it.\nIt's rather cool.\n";

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        private final String text = """
                                    Hello!
                                    This is a text block!
                                    It's multiple lines long.
                                    I can use "quotes" in it.
                                    It's rather cool.
                                    """;

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        private final String text = "Hello!\nThis is a text block!\nIt's multiple lines long.\nI can use \"quotes\" in it.\nIt's rather cool.\n";

While all the decompilers produce valid code, only Procyon is able to turn the string into a text block.

Record patterns
---------------

Record patterns are a Java 21 feature. Taken from: https://github.com/Vineflower/vineflower/blob/1.11.1/testData/src/java21/pkg/TestRecordPatterns1.java

.. tab-set::

   .. tab-item:: Source

      .. code-block:: java
        :substitutions:

        public void test1(R r) {
          if (r instanceof R(int x, Object o)) {
            System.out.println(x);
            System.out.println(o);
          }
        }

        // Not shown in decompiled output for space
        record R(int i, Object o) {}

   .. tab-item:: Vineflower

      .. code-block:: java
        :substitutions:

        public void test1(R r) {
          if (r instanceof R(int var5, Object var8)) {
            System.out.println(var5);
            System.out.println(var8);
          }
        }

   .. tab-item:: Fernflower

      .. code-block:: java
        :substitutions:

        public void test1(R r) {
          if (r instanceof R) {
            R var10000 = r;

            try {
              var9 = var10000.i();
            } catch (Throwable var7) {
              throw new MatchException(var7.toString(), var7);
            }

            int o = var9;
            int x = o;
            var10000 = r;

            try {
              var11 = var10000.o();
            } catch (Throwable var6) {
              throw new MatchException(var6.toString(), var6);
            }

            Object o = var11;
            System.out.println(x);
            System.out.println(o);
          }

        }


   .. tab-item:: CFR

      .. code-block:: java
        :substitutions:

        public void test1(R r) {
          if (r instanceof R) {
            Object object;
            int x;
            R r2 = r;
            try {
              int n;
              x = n = r2.i();
            }
            catch (Throwable throwable) {
              throw new MatchException(throwable.toString(), throwable);
            }
            Object o = object = r2.o();
            System.out.println(x);
            System.out.println(o);
          }
        }

   .. tab-item:: Procyon

      .. code-block:: java
        :substitutions:

        public void test1(final R r) {
          while (true) {
            if (r instanceof R) {
              try {
                final int x = r.i();
                final Object o = r.o();
                System.out.println(x);
                System.out.println(o);
              }
              catch (final Throwable cause) {
                throw new MatchException(cause.toString(), cause);
              }
              return;
            }
            continue;
          }
        }

   .. tab-item:: JD-GUI

      .. code-block:: java
        :substitutions:

        public void test1(R r) {
          if (r instanceof R) {
            R r1 = r;
            try {
              int i = r1.i(), x = i;
              Object object1 = r1.o(), o = object1;
              System.out.println(x);
              System.out.println(o);
            } catch (Throwable throwable) {
              throw new MatchException(throwable.toString(), throwable);
            }
          }
        }

Vineflower is able to decompile the record pattern properly, albeit without variable names. Fernflower doesn't detect the pattern, but it is able to recover the exceptions properly. CFR is unable to recover the trys properly, as this feature is unique for sharing a single exception handler across two different bodies. Procyon also emits an unncessary :java:`while (true)` loop, which leads to incorrect control flow. JD-GUI is also unable to recover the proper exception control flow.
