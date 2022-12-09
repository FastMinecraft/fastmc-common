@file:Suppress("GradlePackageUpdate")

group = "dev.fastmc"
version = "1.0-SNAPSHOT"

plugins {
    java
    kotlin("jvm")
}

allprojects {
    apply {
        plugin("java")
        plugin("kotlin")
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
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    val javaVersion = project.name.substring(4).toInt()
    val fullJavaVersion = if (javaVersion <= 8) "1.$javaVersion" else javaVersion.toString()

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(javaVersion))
        }
    }

    tasks {
        processResources {
            from(rootProject.sourceSets.main.get().resources)
        }

        compileJava {
            source(rootProject.sourceSets.main.get().java)

            options.encoding = "UTF-8"
            sourceCompatibility = fullJavaVersion
            targetCompatibility = fullJavaVersion
        }

        compileKotlin {
            source(rootProject.kotlin.sourceSets["main"].kotlin)

            kotlinOptions {
                jvmTarget = fullJavaVersion
                freeCompilerArgs += listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlin.contracts.ExperimentalContracts",
                )
            }
        }

        jar {
            archiveBaseName.set(rootProject.name)
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlin.contracts.ExperimentalContracts",
            )
        }
    }

    fun disableTask(it: TaskProvider<*>) {
        it.get().enabled = false
    }

    disableTask(compileJava)
    disableTask(compileKotlin)
    disableTask(processResources)
    disableTask(jar)
}