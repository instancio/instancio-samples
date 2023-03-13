# Instancio - `TypeResolver` Sample

A `TypeResolver` allows mapping a type to its subtype.

For example, Instancio does not resolve implementations of abstract types.
However, the `TypeResolver` interface can be implemented to support such functionality.

This sample uses [ClassGraph](https://github.com/classgraph/classgraph/) to resolve
implementation classes automatically by scanning the classpath. Once implemented,
it allows the following use case:


```java
// Given
interface Person { ... }
class PersonImpl implements Person { ... }

// When
Person person = Instancio.create(Person.class);

// Then
assertThat(person).isExactlyInstanceOf(PersonImpl.class);
```
