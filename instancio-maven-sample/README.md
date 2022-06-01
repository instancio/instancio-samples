# Instancio Sample Maven Project

Includes examples of `InstancioExtention` for JUnit 5 and metamodels
generated using `@InstancioMetamodel` annotation.

```
cd instancio-maven-sample
mvn package
```

Note: if your IDE is unable to find the metamodel classes, you might need to mark
`target/generated-test-sources/test-annotations/` as test sources. For example,
in IntelliJ:

<img src="https://i.imgur.com/5Xg0TBB.png" alt="IntelliJ Project Structure"/>

