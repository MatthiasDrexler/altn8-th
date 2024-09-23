import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.changelog") version "2.2.1"
    id("org.jetbrains.intellij.platform") version "2.0.1"
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
}

group = "de.andrena.tools"
val VERSION = "1.0.1"
version = VERSION

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2022.3")
        instrumentationTools()
        jetbrainsRuntimeLocal("/nix/store/mni9lkdmwgdyd8afafmawlrnshbl8zad-jetbrains-jdk-jcef-17.0.8-b1000.8")
        testFramework(TestFrameworkType.Platform)
    }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("junit:junit:4.13.2")
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
    path.set(file("CHANGELOG.md").canonicalPath)
    header.set(provider { "${version.get()} - ${date()}" })
    unreleasedTerm.set("[Unreleased]")
    groups.set(listOf("Added", "Changed", "Fixed", "Removed"))
    itemPrefix.set("*")
    introduction.set(
        """
        Navigate to related files using a shortcut, which is (Alt)(8) by default.
        """.trimIndent()
    )
}
