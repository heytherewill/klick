import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
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

    implementation("com.github.kwhat:jnativehook:2.2.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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