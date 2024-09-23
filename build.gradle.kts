import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jetbrains.changelog") version "2.2.1"
}

group = "de.andrena.tools"
val VERSION = "1.0.1"
version = VERSION

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("io.mockk:mockk:1.13.12")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    type.set("IC")
    version.set("2022.2.5")

    updateSinceUntilBuild.set(false)

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    register<GradleBuild>("buildRelease") {
        tasks = listOf("clean", "test", "buildPlugin", "patchChangelog")
        group = "release"
        description = "Creates a new release with the version specified in build.gradle.kts"
    }

    patchPluginXml {
        sinceBuild.set("222.3345.118")

        changeNotes.set(provider {
            changelog.renderItem(
                changelog
                    .getUnreleased()
                    .withHeader(false)
                    .withEmptySections(false),
                Changelog.OutputType.HTML
            )
        })
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
        channels.set(listOf(System.getenv("PUBLISH_CHANNEL")))
    }
}

changelog {
    version.set(VERSION)
    path.set(file("CHANGELOG.md").canonicalPath)
    header.set(provider { "${version.get()} - ${date()}" })
    introduction.set(
        """
        Navigate to related files using a shortcut, which is (Alt)(8) by default.
        """.trimIndent()
    )
    itemPrefix.set("-")
    keepUnreleasedSection.set(true)
    unreleasedTerm.set("[Unreleased]")
    groups.set(listOf("Added", "Changed", "Fixed", "Removed"))
    lineSeparator.set("\n")
    combinePreReleases.set(true)
}
