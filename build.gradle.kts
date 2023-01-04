group = "dev.fastmc"
version = "1.0-SNAPSHOT"

plugins {
    java
    kotlin("jvm")
    `maven-publish`
    id("dev.fastmc.maven-repo").version("1.0.0")
    id("dev.fastmc.multi-jdk").version("1.1-SNAPSHOT")
}

kotlin {
    val jvmArgs = mutableSetOf<String>()
    (rootProject.findProperty("kotlin.daemon.jvm.options") as? String)
        ?.split("\\s+".toRegex())?.toCollection(jvmArgs)
    System.getProperty("gradle.kotlin.daemon.jvm.options")
        ?.split("\\s+".toRegex())?.toCollection(jvmArgs)
    kotlinDaemonJvmArgs = jvmArgs.toList()
}

java {
    withSourcesJar()
}

multiJdk {
    baseJavaVersion(JavaLanguageVersion.of(8))
    newJavaVersion(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    val kotlinxCoroutineVersion: String by rootProject

    testImplementation(kotlin("test"))
    testImplementation("it.unimi.dsi:fastutil:7.1.0")

    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion")
    compileOnly("it.unimi.dsi:fastutil:7.1.0")
}

publishing {
    publications {
        create<MavenPublication>("root") {
            from(components["multi-jdk"])
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        jvmArgs("-Xmx2G")
    }

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