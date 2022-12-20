# Instancio - Generator Provider SPI Sample

This sample shows how to implement a `GeneratorProvider`.

An implementation of the `GeneratorProvider` interface is used to register
custom generators. This allows Instancio to pick up the registered generators
automatically via the `ServiceLoader` mechanism.
