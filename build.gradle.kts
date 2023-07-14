plugins {
    id("java")
    id("groovy")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

group = "pl.szudor"
version = "0.1"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation("org.spockframework:spock-core:2.3-groovy-2.5")
    implementation("org.springframework.boot:spring-boot:2.2.7.RELEASE")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events ("passed", "skipped", "failed")
    }
}