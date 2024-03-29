plugins {
    id("org.springframework.boot")
}
dependencies {
    implementation(project(mapOf("path" to ":service")))
    implementation(project(mapOf("path" to ":domain")))
    implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
    implementation("org.flywaydb:flyway-mysql:8.5.13")
    implementation("org.springframework.security:spring-security-core:5.7.6")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.yaml:snakeyaml:1.33")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.7")
    implementation("io.jsonwebtoken:jjwt:0.12.3")

    testImplementation("org.testcontainers:spock:1.18.3")
    testImplementation("org.testcontainers:mysql:1.18.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.7")
    testImplementation("org.springframework.boot:spring-boot-starter-jetty:2.7.7")
    testImplementation("org.springframework.security:spring-security-test:5.7.6")
}