package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FileExtensionRelationType
import de.andrena.tools.altn8th.domain.relatedFiles.originIsOnlyRelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedBy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsUnrelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsUnrelatedToAnyFile
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
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
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val anotherRelatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Tests.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(
                    origin,
                    unrelatedFile,
                    relatedFile,
                    anotherRelatedFile
                ),
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with relating regex postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val firstRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val secondRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Tests.kt")
            val thirdRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}UnitTest.kt")
            val fourthRelatingFile = File.from("/is/related/${origin.nameWithoutFileExtension()}UnitTests.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(
                    origin,
                    unrelatedFile,
                    firstRelatingFile,
                    secondRelatingFile,
                    thirdRelatingFile,
                    fourthRelatingFile
                ),
                configuredPostfixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(firstRelatingFile, secondRelatingFile, thirdRelatingFile, fourthRelatingFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files from files with relating postfixes`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile),
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files from files with relating regex postfixes`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile),
                configuredPostfixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating postfixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/${baseName}.component.ts")
            val relatedFile = File.from("/is/related/${baseName}.service.ts")
            val anotherRelatedFile = File.from("/is/related/${baseName}.store.ts")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, relatedFile, anotherRelatedFile),
                configuredPostfixes("\\.component", "\\.service", "\\.store")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating regex postfixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/${baseName}.component.ts")
            val relatedFile = File.from("/is/related/${baseName}.todo-store.ts")
            val anotherRelatedFile = File.from("/is/related/${baseName}.alarm-store.ts")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, relatedFile, anotherRelatedFile),
                configuredPostfixes("\\.component", "\\.[\\-\\w]*store")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with intersecting relating postfixes`() {
            val baseFile = File.from("/is/related/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}UnitTest.kt")
            val relatedFile = File.from("/is/related/${baseFile.nameWithoutFileExtension()}Unit.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile, relatedFile),
                configuredPostfixes("Test", "UnitTest")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile, relatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with postfix with other files with intersecting relating regex postfixes`() {
            val baseFile = File.from("/is/related/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}UnitTest.kt")
            val relatedFile = File.from("/is/related/${baseFile.nameWithoutFileExtension()}Unit.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile, relatedFile),
                configuredPostfixes("Tests?", "UnitTests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile, relatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should not relate itself from base file as origin`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, relatedFile),
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsUnrelatedTo(origin)
            }
        }

        @Test
        fun `should not relate itself from postfix file as origin`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                listOf(origin, baseFile),
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                originIsUnrelatedTo(origin)
            }
        }

        @Test
        fun `should not relate files with some prefix`() {
            val origin = File.from("/is/origin/Origin.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                origin,
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/unrelated/Unrelated${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/Un${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/1${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/_${origin.nameWithoutFileExtension()}Test.kt"),
                File.from("/is/unrelated/${origin.nameWithoutFileExtension()}${origin.nameWithoutFileExtension()}Test.kt")
            )

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test")
            )

            expectThat(result) {
                originIsUnrelatedToAnyFile()
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
                configuredPostfixes("Test")
            )

            expectThat(result) {
                originIsUnrelatedToAnyFile()
            }
        }

        private fun configuredPostfixes(vararg postfixes: String): SettingsState {
            val setting = SettingsState()
            setting.postfixes.clear()
            setting.postfixes.addAll(postfixes.map { PostfixSetting(it, "", "") })
            return setting
        }
    }
}
