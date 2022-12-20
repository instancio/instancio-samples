# Instancio - Generator Provider SPI Sample

`GeneratorProvider` is an interface that maps classes to custom generator implementations.
This mapping is then loaded automatically by Instancio via the `ServiceLoader` mechanism.

Once loaded, you no longer have to manually specify which generator to use. Instead of:

```java
BarGenerator barGenerator = ...;
BazGenerator bazGenerator = ...;

Foo foo = Instancio.of(Foo.class)
    .supply(all(Bar.class), barGenerator)
    .supply(all(Baz.class), bazGenerator)
    .create()
```

you can simply do:

```java
Foo foo = Instancio.create(Foo.class);
```

Generated objects `Foo` and `Bar` can also still be customised using the API:

```java
Foo foo = Instancio.of(Foo.class)
    .set(Bar.class, "value"), "overriden bar value")
    .create()
```

Finally, using the SPI custom generators can also be packaged as a `jar` file
for re-use across different projects.
