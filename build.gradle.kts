group = "dev.fastmc"
version = "1.0-SNAPSHOT"

plugins {
    java
    kotlin("jvm")
    `maven-publish`
    id("dev.fastmc.multi-jdk").version("1.0-SNAPSHOT")
}

kotlin {
    val jvmArgs = mutableSetOf<String>()
    (rootProject.findProperty("kotlin.daemon.jvm.options") as? String)
        ?.split("\\s+".toRegex())?.toCollection(jvmArgs)
    System.getProperty("gradle.kotlin.daemon.jvm.options")
        ?.split("\\s+".toRegex())?.toCollection(jvmArgs)
    kotlinDaemonJvmArgs = jvmArgs.toList()
}

repositories {
    mavenCentral()
}

dependencies {
    val kotlinxCoroutineVersion: String by rootProject

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion")
    compileOnly("it.unimi.dsi:fastutil:7.1.0")
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("root") {
            from(components["multi-jdk"])
        }
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-Xlambdas=indy",
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlin.contracts.ExperimentalContracts",
            )
        }
    }
}