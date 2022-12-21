import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dev.fastmc"
version = "1.1-SNAPSHOT"

plugins {
    java
    kotlin("jvm")
    `maven-publish`
    id("dev.fastmc.maven-repo").version("1.0.0")
    id("dev.fastmc.multi-jdk").version("1.1.0")
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
    defaultJavaVersion(JavaLanguageVersion.of(8))
    newJavaVersion(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    val kotlinxCoroutineVersion: String by rootProject

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion")
    compileOnly("it.unimi.dsi:fastutil:7.1.0")
}

publishing {
    publications {
        create<MavenPublication>("root") {
            from(components["java"])
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

fun SourceSet.configureForJavaVersion(
    javaVersion: JavaLanguageVersion
): SourceSet {
    val fullJavaVersion = if (javaVersion.asInt() <= 8) "1.${javaVersion.asInt()}" else javaVersion.asInt().toString()
    project.afterEvaluate {
        val jarTask = tasks.named(jarTaskName, Jar::class.java) {
            archiveClassifier.set("java${javaVersion.asInt()}")
        }
        artifacts {
            archives(jarTask)
        }
    }

    compileClasspath = sourceSets.main.get().compileClasspath
    runtimeClasspath = sourceSets.main.get().runtimeClasspath
    resources.setSrcDirs(sourceSets.main.get().resources.srcDirs)
    java.setSrcDirs(sourceSets.main.get().java.srcDirs)
    kotlin.setSrcDirs(sourceSets.main.get().kotlin.srcDirs)

    tasks.named(compileJavaTaskName, JavaCompile::class.java) {
        javaToolchains { javaCompiler.set(compilerFor { languageVersion.set(javaVersion) }) }
        options.let { sub ->
            tasks.compileJava.get().options.let { root ->
                sub.encoding = root.encoding
                sub.compilerArgs = root.compilerArgs
                sub.isIncremental = root.isIncremental
                sub.isDeprecation = root.isDeprecation
                sub.isWarnings = root.isWarnings
                sub.isDebug = root.isDebug
                sub.isListFiles = root.isListFiles
                sub.isFailOnError = root.isFailOnError
            }
            sourceCompatibility = fullJavaVersion
            targetCompatibility = fullJavaVersion
        }
    }

    tasks.named(
        getCompileTaskName("kotlin"),
        KotlinCompile::class.java
    ) {
        javaToolchains {
            kotlinJavaToolchain.toolchain.use(launcherFor {
                languageVersion.set(javaVersion)
            })
        }
        kotlinOptions.let { sub ->
            tasks.compileKotlin.get().kotlinOptions.let { root ->
                sub.allWarningsAsErrors = root.allWarningsAsErrors
                sub.apiVersion = root.apiVersion
                sub.freeCompilerArgs = root.freeCompilerArgs
                sub.javaParameters = root.javaParameters
                sub.languageVersion = root.languageVersion
                sub.moduleName = root.moduleName
                sub.noJdk = root.noJdk
                sub.suppressWarnings = root.suppressWarnings
                sub.useK2 = root.useK2
                sub.useOldBackend = root.useOldBackend
                sub.verbose = root.verbose
            }
            sub.jvmTarget = fullJavaVersion
        }
    }

    return this
}