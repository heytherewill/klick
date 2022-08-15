import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("kapt") version "1.7.0"
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev750"
    id("com.diffplug.spotless") version "6.8.0"
}

group = "com.heytherewill"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)

    // Listeners
    implementation("com.github.kwhat:jnativehook:2.2.2")

    // Komposable Architecture
    implementation("com.toggl:komposable-architecture:0.1.1")

    // Dagger
    implementation("com.google.dagger:dagger:2.43.1")
    kapt("com.google.dagger:dagger-compiler:2.43.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Klick"
            packageVersion = "1.0.0"
            copyright = "(c) 2022 William Barbosa. All rights reserved."
            description = "Utilities for making StackOverflows's The Key more useful."

            windows {
                iconFile.set(projectDir.resolve("src/main/resources/icon.ico"))
                installationPath = "C:\\Program Files\\heytherewill\\Klick"
                dirChooser = true
                shortcut = true
                menuGroup = "heytherewill"
            }
        }
    }
}

apply(plugin = "com.diffplug.spotless")

spotless {
    kotlin {
        target("**/*.kt")
        ktlint("0.46.1")
    }
}