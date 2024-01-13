package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FileExtensionRelationType
import de.andrena.tools.altn8th.domain.relatedFiles.originIsOnlyRelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedBy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsUnrelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsUnrelatedToAnyFile
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat

@RunWith(Enclosed::class)
class FindRelatedFilesByPrefixStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files with relating prefixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/I${origin.nameWithoutFileExtension()}.kt")
            val anotherRelatedFile = File.from("/is/related/Abstract${origin.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/TestUnrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(
                    origin,
                    unrelatedFile,
                    relatedFile,
                    anotherRelatedFile
                ),
                configuredPrefixes("I", "Abstract")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with relating regex prefixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val firstRelatingFile = File.from("/is/related/Test${origin.nameWithoutFileExtension()}.kt")
            val secondRelatingFile = File.from("/is/related/Tests${origin.nameWithoutFileExtension()}.kt")
            val thirdRelatingFile = File.from("/is/related/UnitTest${origin.nameWithoutFileExtension()}.kt")
            val fourthRelatingFile = File.from("/is/related/UnitTests${origin.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/TestUnrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(
                    origin,
                    unrelatedFile,
                    firstRelatingFile,
                    secondRelatingFile,
                    thirdRelatingFile,
                    fourthRelatingFile
                ),
                configuredPrefixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(firstRelatingFile, secondRelatingFile, thirdRelatingFile, fourthRelatingFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files from files with relating prefixes`() {
            val baseFile = File.from("/is/related/Base.kt")
            val origin = File.from("/is/origin/Test${baseFile.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/TestUnrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile),
                configuredPrefixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files from files with relating regex prefixes`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/Test${baseFile.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/TestUnrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile),
                configuredPrefixes("(Unit)?Tests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with prefix with other files with relating prefixes`() {
            val baseName = "Origin"
            val origin = File.from("/is/origin/Test${baseName}.kt")
            val relatedFile = File.from("/is/related/I${baseName}.kt")
            val anotherRelatedFile = File.from("/is/related/Abstract${baseName}.ts")
            val unrelatedFile = File.from("/is/unrelated/IUnrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, relatedFile, anotherRelatedFile),
                configuredPrefixes("I", "Abstract", "Test")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with prefix with other files with relating regex prefixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/Test${baseName}.ts")
            val relatedFile = File.from("/is/related/UnitTest${baseName}.ts")
            val anotherRelatedFile = File.from("/is/related/UnitTests${baseName}.ts")
            val unrelatedFile = File.from("/is/unrelated/TestUnrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, relatedFile, anotherRelatedFile),
                configuredPrefixes("[\\w]*Tests?")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with prefix with other files with intersecting relating prefixes`() {
            val baseFile = File.from("/is/related/Origin.kt")
            val origin = File.from("/is/origin/UnitTest${baseFile.nameWithoutFileExtension()}.kt")
            val relatedFile = File.from("/is/related/Test${baseFile.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile, relatedFile),
                configuredPrefixes("Test", "UnitTest")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile, relatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with prefix with other files with intersecting relating regex prefixes`() {
            val baseFile = File.from("/is/related/Origin.kt")
            val relatedFile = File.from("/is/related/Test${baseFile.nameWithoutFileExtension()}.kt")
            val origin = File.from("/is/origin/UnitTest${baseFile.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, unrelatedFile, baseFile, relatedFile),
                configuredPrefixes("Test", "UnitTest")
            )

            expectThat(result) {
                originIsOnlyRelatedTo(baseFile, relatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should not relate itself from base file as origin`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/Test${origin.nameWithoutFileExtension()}.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, relatedFile),
                configuredPrefixes("Test", "Tests")
            )

            expectThat(result) {
                originIsUnrelatedTo(origin)
            }
        }

        @Test
        fun `should not relate itself from prefix file as origin`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/Test${baseFile.nameWithoutFileExtension()}.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                listOf(origin, baseFile),
                configuredPrefixes("Test", "Tests")
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
                File.from("/is/unrelated/TestUnrelated.kt"),
                File.from("/is/unrelated/Test${origin.nameWithoutFileExtension()}Unrelated.kt"),
                File.from("/is/unrelated/Test${origin.nameWithoutFileExtension()}1.kt"),
                File.from("/is/unrelated/Test${origin.nameWithoutFileExtension()}_.kt"),
                File.from("/is/unrelated/Test${origin.nameWithoutFileExtension()}${origin.nameWithoutFileExtension()}.kt")
            )

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPrefixes("Test")
            )

            expectThat(result) {
                originIsUnrelatedToAnyFile()
            }
        }

        @Test
        fun `should not relate files without relating prefixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                origin,
                File.from("/is/unrelated/Unrelated${origin.nameWithoutFileExtension()}.kt"),
                File.from("/is/unrelated/TestUnrelated${origin.nameWithoutFileExtension()}.kt")
            )

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPrefixes("Test")
            )

            expectThat(result) {
                originIsUnrelatedToAnyFile()
            }
        }

        private fun configuredPrefixes(vararg prefixes: String): SettingsState {
            val setting = SettingsState()
            setting.prefixes.clear()
            setting.prefixes.addAll(prefixes.map { PrefixSetting(it, "", "") })
            return setting
        }
    }
}
