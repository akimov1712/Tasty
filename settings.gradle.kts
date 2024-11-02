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
include(":features:main:tabs")
include(":features:main:tabs:recipes")
include(":features:main:tabs:add_recipe")
include(":features:main:tabs:category")
include(":features:main:tabs:profile")
include(":features:main:tabs:favorite")
include(":features:main:detail_recipe")
include(":features:main")
