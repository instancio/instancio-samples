# Instancio Sample Projects

### [`instancio-models-sample`](instancio-models-sample)

Sample project used for the article: [Creating object templates using Models](https://www.instancio.org/articles/creating-object-templates-using-models/)

### [`book-mapper-sample`](book-mapper-sample)

A small sample illustrating reduction in code by eliminating manual data setup.

### [`generator-provider-sample`](generator-provider-sample)

A `GeneratorProvider` that automatically maps generators to classes/fields.
This removes the need to specify custom generators manually via the API.

### [`annotation-processor-sample`](annotation-processor-sample)

A sample implementation of `AnnotationProcessor` for handling custom annotations.

### [`type-resolver-sample`](type-resolver-sample)

A custom `TypeResolver` that scans the classpath to find implementation classes for interfaces.
This eliminates the need for specifying `subtype()` manually.
Classpath scanning is implemented using [ClassGraph](https://github.com/classgraph/classgraph).
