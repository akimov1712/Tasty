pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tasty"
include(":app")
include(":domain")
include(":data")
include(":core:common")
include(":core:android")
include(":core:ui")
include(":navigation")
include(":features:tabs")
include(":features:tabs:recipes")
include(":features:tabs:add_recipe")
include(":features:tabs:category")
include(":features:tabs:profile")
include(":features:tabs:favorite")
