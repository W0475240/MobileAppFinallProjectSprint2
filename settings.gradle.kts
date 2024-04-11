pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoiYmFoa2hvIiwiYSI6ImNsdTh5ZG1udDA0M24ybG40MnJxcHJ6emQifQ.xvlx0MqTH2snePSIAAhx-Q"

            }
        }
    }
}

rootProject.name = "TransitApp"
include(":app")
 