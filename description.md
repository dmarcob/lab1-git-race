# Documentation

## Set up

For building and running the app you will need the next resources:

- Kotlin, you can get it following the next [web](https://kotlinlang.org/docs/command-line.html)
- Java11, you can easily download it in the official oracle web or use alternatives libraries such as <https://adoptopenjdk.net/>

## Building the app

For building it you only have to use the tool `gradlew`, for listing all the options use the tasks command.

On the list you will see two important commands: `build` and `bootRun`, the first for building and the other for running the aplication.

For checking if it works you can find the next page on <http://localhost:8080>:

![image](https://user-images.githubusercontent.com/46299278/133663363-de39b281-131e-4c4e-8e35-e32b4c5c18a5.png)

## Testing the app

for testing all the unitary test run the command:

```bash
gradlew check
```

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

## Deployment

### With a Dockerfile

It's very simple, just follow the following steps:

1. Run following commands. This if going to create the image that we need:

   ```bash
   docker pull gradle:openj9
   docker build -t lab1-git-race .
   ```

1. If all went correctly, a image has been created (Image ID and Size may be different):

    ```bash
    $ docker images
    REPOSITORY      TAG       IMAGE ID       CREATED          SIZE
    lab1-git-race   latest    6de6b5e29bda   1 minutes ago   709MB
    ```

1. Finally, the following command run the container and is going to link the port 8080 of the container with the port 8080 of the host. This can be changed for example `5000:8080` to link the port 8080 of the container with the port 5000 of the host.

    ```bash
    docker run -p 8080:8080 lab1-git-race
    ```

#### Alternative approach

For runnicng the app inside a container run the next command:

```bash
docker run --expose=8080 --network="host" --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle gradle bootRun
```

If you get permision denied try to use root privileges or adding you user to the docker group for using it without sudo.

```bash
sudo usermod -aG docker $USER`
```

### With a docker-compose

In order to run the app, you will need the following tools:

- [docker](https://docs.docker.com/engine/install/)
- [docker-compose](https://docs.docker.com/compose/install)

Run the following command to build the images if needed and run the app:

```bash
docker-compose up
```

It might show the following error:

```text
ERROR: Couldn't connect to Docker daemon at [...]
```

Try running with sudo:

```bash
sudo docker-compose up
```

Now you can access [the website](http://localhost:8080/) to check if everything works correctly.

**Note:** By default, local port 8080 is used to deploy the app, however, it can be modified in ``docker-compose.yml``, setting ``8080:8080`` to ``<your-port>:8080``.

## With Kuvernetes

Using Tomas' [Dockerfile](https://github.com/Tomenos18/lab1-git-race/blob/master/Dockerfile) and [Guide for the basic Docker Deployment](https://github.com/Tomenos18/lab1-git-race/blob/master/README.md)

Requirements:

- Minikube single node cluster
- Kubectl
- Docker
- Helm

Steps to reproduce (adapted from [this tutorial](https://dev.to/gateixeira/deploying-a-spring-boot-kotlin-app-on-kubernetes-with-docker-and-helm-589p)):

1. Create the following files:
  
   - `Dockerfile` in root dir
   - `/charts/values.yaml`
   - `/charts/Chart.yaml`
   - `/charts/templates/deployment.yaml`
   - `/charts/templates/service.yaml`
  
  The only difference with the original tutorial is in `values.yaml` file, where `appName` is now `demo-minikube`, and `image/registry` is now your DockerID.

1. Switch Docker environment (from `Dockerfile` directory):
  
   - MacOS / Linux:

     ```bash
     eval $(minikube docker-env)
     ```

   - Windows:

     ```cmd
     minikube docker-env | Invoke-Expression
     ```

1. Build image:

   ```bash
   docker build -t <DockerID>/demo-minikube:latest .
   ```

1. Install App inside Minikube:

   ```bash
   helm upgrade --install demo-minikube charts --values charts/values.yaml
   ```

1. Tunnel app to publicly accesible url:

   ```bash
   minikube service demo-minikube --url
   ```

From here, a URL specifying the localhost should appear.

### On Heroku

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
