
plugins {
    kotlin("kapt")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")

}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.7")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("com.querydsl:querydsl-apt:5.0.0")
    kapt("javax.annotation:javax.annotation-api:1.3.2")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
}