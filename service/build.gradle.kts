import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("groovy")
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
    testImplementation("org.spockframework:spock-core:2.3-groovy-2.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.2.7.RELEASE")
    implementation("org.springframework.boot:spring-boot:2.2.7.RELEASE")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation(kotlin("stdlib-jdk8"))
}
tasks.test {
    useJUnitPlatform()
    testLogging {
        events ("passed", "skipped", "failed")
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}