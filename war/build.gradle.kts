configurations {
    testImplementation.get().exclude("spring-boot-starter-tomcat")
}
plugins {
    id("application")
}
dependencies {
    implementation(project(mapOf("path" to ":service")))
    implementation(project(mapOf("path" to ":domain")))
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.flywaydb:flyway-mysql:8.5.13")

    testImplementation("org.testcontainers:spock:1.18.3")
    testImplementation("org.testcontainers:mysql:1.18.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.7")
    testImplementation("org.springframework.boot:spring-boot-starter-jetty:2.7.7")

    implementation("org.yaml:snakeyaml:1.33")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.7")
}

application {
    mainClass.set("pl.szudor.CinemaApp")
}