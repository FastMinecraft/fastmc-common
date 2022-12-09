rootProject.name = "fastmc-common"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    val kotlinVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm").version(kotlinVersion)
    }
}

include("java8", "java17")