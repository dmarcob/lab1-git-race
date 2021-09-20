# Documentation

## Controller

The file `src/main/controller/HelloController.kts` contains the implementation of the controller of this web application following the MVC pattern.
The handler function `welcome()` serves the view `src/main/resources/templates/welcome.html`.

## Error

The file `error.html` is a custom error page which is under `src/main/resources/templates/` for Spring MVC to detecting it automatically. The model variable `error` is passed to the view `error.html` to know what kind of error ocurred.

## Gradle Configuration

Gradle is a tool for automating building.  
The Gradle build file `build.gradle.kts` located in the main directory specifies Gradle's configuration for this project.

The build file consists of 4 main sections:

- Plugins
- Repositories
- Dependencies
- Kotlin
- Kotlin compiler options

### Plugins

Plugins are extensions that add features to Gradle.
In this project we add plugins for [Kotlin](https://github.com/JetBrains/kotlin) and [SpringBoot](https://github.com/spring-projects/spring-boot).

### Repositories

In this section the repository for solving dependencies is declared.  
In our case, `mavenCentral()` specifies that the [Maven Central public repository](https://repo.maven.apache.org/maven2/) will be used to solve dependencies.

### Dependencies

This sections contains the dependencies of the project which will be downloaded from the repository specified in the previous section.  
The dependencies used in this project are:

- [SpringBoot](https://github.com/spring-projects/spring-boot)
- [Jackson](https://github.com/FasterXML/jackson)
- [Kotlin Reflection](https://kotlinlang.org/docs/reflection.html#jvm-dependency)
- Kotlin Standard Library JDK 8 extension
- [Bootstrap WebJar](https://github.com/webjars/bootstrap)

### Kotlin

Kotlin is configured to run on the JVM.
When the Kotlin targets the JVM platform, options of the compile task are specified in the `compileKotlin` variable.
In our case, we specify that the target version of the JVM is 11 with `jvmTarget` and we configure the compiler to generate error by adding the `-Xjsr305=strict` flag.

## How to test the code

Testing is handled using JUnit, a powerful framework that allows you to check different aspects of your code.

Unit tests can be run with the following commands.

```bash
cd lab1-git-race
gradle test
```

All verification tasks, including unit tests, can be run with the following commands. Gradle offers a flag, -i, that can be used to show more information while running the checks.

```bash
cd lab1-git-race
gradle check
```

### Integration Tests

There's 3 tests, stored at `src/test/kotlin`, that have been made for this Kotlin Webpage

---

### HTML/CSS Tests

The file `src/test/kotlin/IntegrationTest.kt` contains two tests that checks the main behaivour of the HTML page itself:

- `testHome()` checks if making a request at `http://localhost:$port` (With `$port` in this case being 0 for the shake of the test), yields both:

  - A `OK` HTTP Status Code.
  - A HTML body with `<title>hello`.

    If this happens, we can assume the webpage's HTML is the one intended.
- `testCss()` checks if the CSS of the webpage has basic functionality. For this, it request `http://localhost:$port/webjars/bootstrap/5.1.0/css/bootstrap.min.css` from the Web Server, and checks if it has a response with:

  - A `OK` HTML Status.
  - A body with `"body"`.
  - A file with a header equal to that of `"text/css"`.

This ensures the webpage has a valid CSS file.

## How to deploy the app with Heroku

> In order to deploy the app, you will need `git` and `heroku` CLI installed in your machine.

Detailed instructions can be found [here](https://devcenter.heroku.com/articles/git#creating-a-heroku-remote).

---

First of all, create an empty app:

```bash
$ heroku create
Creating app... done, ⬢ thawing-inlet-61413
https://thawing-inlet-61413.herokuapp.com/ | https://git.heroku.com/thawing-inlet-61413.git
```

Save the url, it will be needed.

Now, add a remote to your local repository with the `heroku git:remote` CLI command. All you need is your Heroku app's name:

```bash
$ heroku git:remote -a thawing-inlet-61413
set git remote heroku to https://git.heroku.com/thawing-inlet-61413.git
```

Deploy the code!

```bash
git push heroku master
```

Do not be afraid of detaching the `push` command, it won't cancel the build and the app will be deployed anyways.
