plugins{
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

configurations {
    testImplementation.get().exclude("spring-boot-starter-tomcat")
}

dependencies {
    api(project(":shared"))
    api(project(":service"))
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")
    testImplementation("org.testcontainers:spock:1.18.3")
    testImplementation("org.testcontainers:mysql:1.18.3")
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
    testImplementation("org.spockframework:spock-spring:2.0-groovy-3.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.7")
    testImplementation("com.github.tomakehurst:wiremock:2.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-jetty:2.7.7")




    implementation("com.mysql:mysql-connector-j:8.0.33")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.7")
}
tasks.test {
    useJUnitPlatform()
}