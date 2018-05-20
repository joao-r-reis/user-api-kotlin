# user-api-kotlin
Very basic web api to learn kotlin and some frameworks like ktor. This is also almost my first web api project in the JVM!

## Frameworks and libraries

Web Api:
- Ktor (with coroutines)
    - Netty
    - Gson
    - Koin (Dependency Injection)
- KMongo (with coroutines)
- Logback

Tests:
- Mockito
- Embedded MongoDB
- Ktor server test host

## Building and Testing

To build and run integration and unit tests use gradle:

```bash
./gradlew build
```

The test run result will be printed to the console like this:

```bash
Results: SUCCESS (9 tests, 9 successes, 0 failures, 0 skipped)
```

If you get an error keep in mind that this project was tested against JDK 8. Try installing JDK 8 and setting JAVA_HOME env variable to the jdk's folder.