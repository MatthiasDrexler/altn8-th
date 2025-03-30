import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.changelog") version "2.2.1"
    id("org.jetbrains.intellij.platform") version "2.1.0"
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
}

group = "de.andrena.tools"
version = "1.0.8"

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
        jetbrainsRuntimeLocal("/nix/store/qlg7394lwg1v0jlywjlazy7x5rysn248-jetbrains-jdk-jcef-21.0.4-b598.4")
        pluginVerifier()
        testFramework(TestFrameworkType.Platform)
    }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("junit:junit:4.13.2")
}

intellijPlatform {
    projectName = project.name
    pluginConfiguration {
        id = "de.andrena.tools.altn8-th"
        name = "AltN8-TH"
        version = project.version.toString()
        vendor {
            name = "MuehlburgPhoenix"
            url = "https://github.com/MatthiasDrexler/altn8-th"
            email = "muehlburgphoenix@declarative.mozmail.com"
        }
        description = """
            <p>
                <strong>AltN8-TH</strong> allows you to quickly navigate to related files.
                Related files can be specified to your liking using regular expressions.
                This functionality allows developers to quickly access files that are contextually linked,
                improving workflow efficiency.
            </p>

            <p>
                For example:
            </p>
            <ul>
                <li>Jump from source to test file.</li>
                <li>Jump from <code>angular.component.ts</code> to <code>angular.component.html</code> or <code>angular.component.css</code>.</li>
                <li>...</li>
            </ul>

            <h2>Features</h2>
            <ul>
                <li>Specify contextually linked files using regex patterns.</li>
                <li>Navigate to related files using a keyboard shortcut (by default <code>[ALT][8]</code>, <code>[CMD][ALT][8]</code> on macOS).</li>
                <li>If more than one related file is found, a popup lets you choose the specific file.</li>
            </ul>

            <h2>Special Thanks</h2>
            <p>
                Special thanks to <strong>LeapingFrogs.com</strong> for the original AltN8 plugin and
                <strong>Minas Manthos</strong> for his further development of the original plugin.
                Both plugins served as ideological foundation for this version.
            </p>
        """.trimIndent()

        ideaVersion {
            sinceBuild = "223"
            untilBuild = provider { null }
        }

        changeNotes = provider {
            changelog.renderItem(
                changelog
                    .getUnreleased()
                    .withHeader(false)
                    .withEmptySections(false),
                Changelog.OutputType.HTML
            )
        }
    }
    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
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
        tasks = listOf("clean", "test", "patchChangelog", "buildPlugin")
        group = "release"
        description = "Creates a new release with the version specified in build.gradle.kts"
    }
}

changelog {
    path.set(file("CHANGELOG.md").canonicalPath)
    header.set(provider { "${version.get()} - ${date()}" })
    unreleasedTerm.set("[Unreleased]")
    groups.set(listOf("Added", "Changed", "Fixed", "Removed"))
    itemPrefix.set("*")
    patchEmpty.set(true)
    introduction.set(
        """
        Navigate to related files using a shortcut, which is (Alt)(8) by default.
        """.trimIndent()
    )
}
