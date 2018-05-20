# User Api in Kotlin

Very basic web api to learn kotlin and some frameworks like ktor. This is also almost my first web api project in the JVM!

## Frameworks and libraries

### Web Api:
- Ktor (with coroutines)
    - Netty
    - Gson
    - Koin (Dependency Injection)
- KMongo (with coroutines)
- Logback

### Tests:
- Mockito
- Embedded MongoDB
- Ktor server test host

## Building and Testing

To build and run integration and unit tests run the following command:
```bash
./gradlew build
```

The build task compiles the project, runs linter verification task and runs automated tests (integration and unit tests). To run these tests you don't need a mongodb instance running, they are completely independent and run in the same process as the web api itself.

The test run result will be printed to the console like this:
```bash
Results: SUCCESS (9 tests, 9 successes, 0 failures, 0 skipped)
```

If you get an error while running gradle keep in mind that this project was tested against JDK 8. Try installing JDK 8 and setting JAVA_HOME env variable to the jdk's folder.

## Running the project

First start a mongodb instance with docker:
```bash
docker run --name mongo -p 27017:27017 -d mongo
```

Then start the project with gradle:
```bash
./gradlew run
```

### Getting users

```bash
curl -s http://localhost:8080/api/v1/users
[
  {
    "name": "testuser",
    "age": 49,
    "id": "9ccad733-1f78-45f4-9b59-650a6a893160"
  }
]
```

### Creating a user
```bash
curl -s --header "Content-Type: application/json" --request POST \
--data '{"name":"testuser","age":49}' http://localhost:8080/api/v1/users
{
  "name": "testuser",
  "age": 49,
  "id": "9ccad733-1f78-45f4-9b59-650a6a893160"
}
```

## Automatically formatting code with the linter

To automatically format the code according to the linter's ruleset do the following:

```bash
./gradlew ktlintFormat
```

The issues that the linter isn't able to resolve on its own are printed to the stderr.