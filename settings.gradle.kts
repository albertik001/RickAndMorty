pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()

        maven { url = uri("https://jitpack.io") }
    }
}

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven("geek-studio")
        maven { url = uri("https://jitpack.io") }
    }

    versionCatalogs {
        create("config") {
            from(files("gradle/config.versions.toml"))
        }
        create("options") {
            from(files("gradle/options.versions.toml"))
        }

    }
}

rootProject.name = "RickAndMorty"
include(":app", ":data", ":domain")
