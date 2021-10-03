// Adds Kotlin compile task to the Gradle build
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Adds Spring Boot and kotlin plugins
plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.spring") version "1.5.30"
}

// Sets groupID and version of the task
group = "es.unizar.webeng"
version = "0.0.1-SNAPSHOT"

// Sets remote dependency repository to Maven Central
repositories {
    mavenCentral()
}

// Declares dependencies for implementation and testing
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.webjars:bootstrap:5.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    
}

// Makes Kotlin use the JVM 11 toolchain
kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// Sets compiler options to generetate errors with
// null pointers and target version 11 of JVM
val compileKotlin: KotlinCompile by tasks
with(compileKotlin.kotlinOptions) {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
}

// Configure tests to run with the JUnit testing framework
val test: Test by tasks
test.useJUnitPlatform()