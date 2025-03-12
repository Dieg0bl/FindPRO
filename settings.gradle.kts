pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoiZGllZ29iYXJyZWlybyIsImEiOiJjbTVzNGp6ZW4wNzkxMmhyNW5hdHZzYnV3In0.JF_xn542f_cIJT2NyQfUYA"
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoiZGllZ29iYXJyZWlybyIsImEiOiJjbTVzNGp6ZW4wNzkxMmhyNW5hdHZzYnV3In0.JF_xn542f_cIJT2NyQfUYA"
            }
        }
    }
}
rootProject.name = "FindPro"
include(":app")
include(":core")
include(":mapservice")
include(":authentication")
include(":userprofiles")
