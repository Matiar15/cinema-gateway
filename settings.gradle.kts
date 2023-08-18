rootProject.name = "cinema-gateway"
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}
include("shared")
include("service")
include("war")
include("domain")
include("common")
