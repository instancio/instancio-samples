# Instancio Models - Sample Maven Project

This sample demonstrates how Instancio `Model`s can help

- simplify data set up unit tests
- significantly reduce boilerplate code
- provide flexibility in manipulating data for different test cases

The sample code is a service that maps a domain object to an Avro object.
In order to run the tests, Avro classes must be generated first.
This can be done by running:

```sh
mvn package
```

The code in this sample is described in the following article:

https://www.instancio.org/articles/creating-object-templates-using-models/
