
plugins {
    val kotlinVersion = "1.6.21"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    id("java")
    id("groovy")
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-groovy-3.0")

    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("org.springframework.boot:spring-boot:2.7.7")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
}