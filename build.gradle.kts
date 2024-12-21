plugins {
    java
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val springBootVersion = "2.7.0"
val apachePoiVersion = "5.3.0"
val postgresVersion = "42.7.4"
val lombokVersion = "1.18.36"
val mapstructVersion = "1.6.3"
val mapstructLombokBindingVersion = "0.2.0"
val testcontainersVersion = "1.20.4"

dependencies {

    // SpringBoot Starters
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DevTools
    implementation("org.springframework.boot:spring-boot-devtools")

    // Apache POI
    implementation("org.apache.poi:poi-ooxml:$apachePoiVersion")

    // PostgreSQL
    implementation("org.postgresql:postgresql:$postgresVersion")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // TestContainers
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$mapstructLombokBindingVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
