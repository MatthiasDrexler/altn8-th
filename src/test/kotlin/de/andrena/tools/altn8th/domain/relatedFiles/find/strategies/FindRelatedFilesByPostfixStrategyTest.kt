package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.PostfixRelationType
import de.andrena.tools.altn8th.domain.relatedFiles.originIsNotRelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsNotRelatedToAnyFile
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedBy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedTo
import de.andrena.tools.altn8th.domain.settings.SettingsState
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat

@RunWith(Enclosed::class)
class FindRelatedFilesByPostfixStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files with relating postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val anotherRelatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Tests.kt")
            val allFilesContainingRelatedFiles = listOf(
                origin,
                unrelatedFile,
                relatedFile,
                anotherRelatedFile
            )

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsRelatedTo(relatedFile)
                originIsRelatedTo(anotherRelatedFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files with relating regex postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val firstRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val secondRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Tests.kt")
            val thirdRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}UnitTest.kt")
            val fourthRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}UnitTests.kt")
            val allFilesContainingRelatedFiles = listOf(
                origin,
                unrelatedFile,
                firstRelatingFile,
                secondRelatingFile,
                thirdRelatingFile,
                fourthRelatingFile
            )

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsRelatedTo(firstRelatingFile)
                originIsRelatedTo(secondRelatingFile)
                originIsRelatedTo(thirdRelatingFile)
                originIsRelatedTo(fourthRelatingFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files from files with relating postfixes`() {
            val baseFile = File.from("/is/origin/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val allFilesContainingRelatedFiles = listOf(origin, unrelatedFile, baseFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsRelatedTo(baseFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files from files with relating regex postfixes`() {
            val baseFile = File.from("/is/origin/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val allFilesContainingRelatedFiles = listOf(origin, unrelatedFile, baseFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsRelatedTo(baseFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating postfixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/${baseName}.component.ts")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val relatedFile = File.from("/is/origin/${baseName}.service.ts")
            val anotherRelatedFile = File.from("/is/origin/${baseName}.store.ts")
            val allFilesContainingRelatedFiles = listOf(origin, unrelatedFile, relatedFile, anotherRelatedFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("\\.component", "\\.service", "\\.store")
            )

            expectThat(result) {
                originIsRelatedTo(relatedFile)
                originIsRelatedTo(anotherRelatedFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating regex postfixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/${baseName}.component.ts")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val relatedFile = File.from("/is/origin/${baseName}.todo-store.ts")
            val anotherRelatedFile = File.from("/is/origin/${baseName}.alarm-store.ts")
            val allFilesContainingRelatedFiles = listOf(origin, unrelatedFile, relatedFile, anotherRelatedFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("\\.component", "\\.[\\-\\w]*store")
            )

            expectThat(result) {
                originIsRelatedTo(relatedFile)
                originIsRelatedTo(anotherRelatedFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with intersecting relating postfixes`() {
            val baseFile = File.from("/is/related/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}UnitTest.kt")
            val relatedFile = File.from("/is/related/${baseFile.nameWithoutFileExtension()}Unit.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val allFilesContainingRelatedFiles = listOf(origin, unrelatedFile, baseFile, relatedFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Test", "UnitTest")
            )

            expectThat(result) {
                originIsRelatedTo(baseFile)
                originIsRelatedTo(relatedFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with intersecting relating regex postfixes`() {
            val baseFile = File.from("/is/related/Origin.kt")
            val anotherBaseFile = File.from("/is/related/${baseFile.nameWithoutFileExtension()}Unit.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}UnitTest.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")
            val allFilesContainingRelatedFiles = listOf(origin, unrelatedFile, baseFile, anotherBaseFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Tests?", "UnitTests?")
            )

            expectThat(result) {
                originIsRelatedTo(baseFile)
                originIsRelatedTo(anotherBaseFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(PostfixRelationType())
            }
        }

        @Test
        fun `should not relate itself from base file as origin`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val allFilesNotContainingRelatedFiles = listOf(origin, relatedFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsNotRelatedTo(origin)
            }
        }

        @Test
        fun `should not relate itself from postfix file as origin`() {
            val baseFile = File.from("/is/origin/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val relatedFile = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Tests.kt")
            val allFilesNotContainingRelatedFiles = listOf(origin, baseFile, relatedFile)

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsNotRelatedTo(origin)
            }
        }

        @Test
        fun `should not relate files with some prefix`() {
            val origin = File.from("/is/origin/Origin.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                origin,
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/unrelated/Unrelated${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/Unrelated${origin.nameWithoutFileExtension()}Tests.kt"),
                File.from("/is/unrelated/Un${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/1${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/_${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/${origin.nameWithoutFileExtension()}${origin.nameWithoutFileExtension()}Test.kt")
            )

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsNotRelatedToAnyFile()
            }
        }

        @Test
        fun `should not relate files without relating postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                origin,
                File.from("/is/unrelated/${origin.nameWithoutFileExtension()}Unrelated.kt"),
                File.from("/is/unrelated/${origin.nameWithoutFileExtension()}UnrelatedTest.kt")
            )

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsNotRelatedToAnyFile()
            }
        }

        private fun configuredPostfixes(vararg postfixes: String): SettingsState {
            val setting = SettingsState()
            setting.postfixes.addAll(postfixes)
            return setting
        }
    }
}
