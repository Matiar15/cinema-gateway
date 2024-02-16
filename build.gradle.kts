plugins {
    id("application")
    val kotlinVersion = "1.6.21"
    id("org.springframework.boot") version "2.7.7"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("java")
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
}

application {
    mainClass.set("pl.szudor.CinemaApp")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "pl.szudor.CinemaApp"
}

dependencies {
    implementation(project(mapOf("path" to ":war")))
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

allprojects {
    apply {
        plugin("kotlin")
        plugin("java")
        plugin("groovy")
    }

    sourceSets.main {
        java.srcDirs("src/main/java", "src/main/kotlin")
    }

    repositories {
        mavenCentral()
    }

    group = "pl.szudor"
    version = "1.0.0"

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.test {
        useJUnitPlatform()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("groovy")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencies {
        testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
        testImplementation("org.spockframework:spock-spring:2.3-groovy-4.0")
        implementation("org.springframework.boot:spring-boot-starter-security:2.7.7")
        implementation("org.springframework.boot:spring-boot-starter-test:2.7.7")
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        implementation("org.springframework.boot:spring-boot:2.7.7")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
        implementation("com.mysql:mysql-connector-j:8.0.33")
        implementation(kotlin("reflect"))
        implementation("com.google.guava:guava:32.0.1-jre")

    }
}