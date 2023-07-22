plugins {
    id("java")
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.0"
}
configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


group = "pl.szudor"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.2.7.RELEASE")
    implementation("org.springframework.boot:spring-boot:2.2.7.RELEASE")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.spring.dependency-management:io.spring.dependency-management.gradle.plugin:1.1.1")
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.h2database:h2:1.4.200")
    implementation(project(":war"))
    implementation(project(":service"))
}
