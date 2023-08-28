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

dependencies {
    api(project(":domain"))
    api(project(":shared"))
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-groovy-3.0")
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("jakarta.validation:jakarta.validation-api:2.0.2")

}

tasks.test {
    useJUnitPlatform()
}