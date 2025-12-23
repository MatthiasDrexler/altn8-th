import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    alias(libs.plugins.jetbrains.changelog)
    alias(libs.plugins.jetbrains.intellij.platform)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "de.andrena.tools"
version = "2.1.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.3")
        jetbrainsRuntimeLocal(
            System.getenv("JETBRAINS_SDK")
                ?: "/nix/store/d6pslcl320dfkcjmimf4i65wjp3kdj08-jetbrains-jdk-jcef-21.0.8-b1148.57"
        )
        pluginVerifier()
        testFramework(TestFrameworkType.Starter)
    }

    implementation(libs.kotlinx.serialization.core)

    testImplementation(libs.strikt.core)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    integrationTestImplementation(libs.kotlinx.coroutines.core.jvm)
    integrationTestImplementation(libs.kodein.di)
    integrationTestImplementation(libs.jetbrains.driver)
    integrationTestImplementation(libs.jetbrains.ide.starter)
    integrationTestImplementation(libs.jetbrains.ide.starter.driver)
    "integrationTestRuntimeOnly"("org.junit.platform:junit-platform-launcher")
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
            sinceBuild = "243"
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
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    test {
        useJUnitPlatform()
    }

    register<GradleBuild>("buildRelease") {
        tasks = listOf("clean", "test", "patchChangelog", "buildPlugin")
        group = "release"
        description = "Creates a new release with the version specified in build.gradle.kts"
    }
}

val integrationTest = task<Test>("integrationTest") {
    val integrationTestSourceSet = sourceSets.getByName("integrationTest")
    testClassesDirs = integrationTestSourceSet.output.classesDirs
    classpath = integrationTestSourceSet.runtimeClasspath
    systemProperty("path.to.build.plugin", tasks.prepareSandbox.get().pluginDirectory.get().asFile)
    useJUnitPlatform()
    dependsOn(tasks.prepareSandbox)
}

idea {
    module {
        testSources.from(sourceSets["integrationTest"].allSource.srcDirs)
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
