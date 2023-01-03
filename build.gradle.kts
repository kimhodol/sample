import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Versions.Spring.Boot
    id("io.spring.dependency-management") version Versions.Spring.DependencyManagement
    kotlin("jvm") version Versions.Kotlin
    kotlin("plugin.spring") version Versions.Kotlin
    kotlin("plugin.jpa") version Versions.Kotlin
}

group = "dev.hodol"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.kotest:kotest-runner-junit5:${Versions.Kotest.Core}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.Kotest.Core}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${Versions.Kotest.SpringExtension}")
    testImplementation("com.ninja-squad:springmockk:${Versions.SpringMockK}")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${Versions.TestContainers}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
