plugins {
    id("org.jetbrains.kotlin.plugin.jpa")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.7")
    implementation("com.querydsl:querydsl-jpa:5.0.0")
    implementation(project(mapOf("path" to ":domain")))
    implementation("io.jsonwebtoken:jjwt:0.12.3")
}
