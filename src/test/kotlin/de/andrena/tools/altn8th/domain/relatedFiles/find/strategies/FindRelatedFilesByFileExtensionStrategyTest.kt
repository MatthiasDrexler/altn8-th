package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FileExtensionRelationType
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsNotRelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedBy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedTo
import de.andrena.tools.altn8th.domain.settings.SettingsState
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat

@RunWith(Enclosed::class)
class FindRelatedFilesByFileExtensionStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files with same name and different allowed extension`() {
            val originAndBaseFile = File.from("/is/origin/angular.component.ts")
            val relatedFile = File.from("/is/related/${originAndBaseFile.nameWithoutFileExtension()}.css")
            val anotherRelatedFile = File.from("/is/related/${originAndBaseFile.nameWithoutFileExtension()}.html")
            val unrelatedFile = File.from("/is/unrelated/another.component.ts")
            val allFiles = listOf(originAndBaseFile, relatedFile, unrelatedFile, anotherRelatedFile)

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                originAndBaseFile,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsRelatedTo(relatedFile)
                originIsRelatedTo(anotherRelatedFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with same name and extension at different path`() {
            val originAndBaseFile = File.from("/is/origin/file.kt")
            val relatedAtAnotherPath = File.from("/is/related/${originAndBaseFile.nameWithFileExtension()}")
            val unrelatedFile = File.from("/is/unrelated.ts")
            val allFiles = listOf(originAndBaseFile, unrelatedFile, relatedAtAnotherPath)

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                originAndBaseFile,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsRelatedTo(relatedAtAnotherPath)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with allowed extension from origin with excluded extension`() {
            val allowedFileExtension = "ts"
            val excludedFileExtension = "js"

            val originAndBaseFile = File.from("/is/origin/file.${excludedFileExtension}")
            val relatedFile =
                File.from("/is/related/${originAndBaseFile.nameWithoutFileExtension()}.${allowedFileExtension}")
            val unrelatedFile = File.from("/is/unrelated.txt")
            val allFiles = listOf(originAndBaseFile, relatedFile, unrelatedFile)

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                originAndBaseFile,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsRelatedTo(relatedFile)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should not relate files with excluded file extension`() {
            val allowedFileExtension = "ts"
            val excludedFileExtension = "js"

            val originAndBaseFile = File.from("/is/origin/file.txt")
            val fileWithExcludedFileExtension =
                File.from("/is/unrelated/${originAndBaseFile.nameWithoutFileExtension()}.${excludedFileExtension}")
            val fileWithAllowedFileExtension =
                File.from("/is/related/${originAndBaseFile.nameWithoutFileExtension()}.${allowedFileExtension}")
            val unrelatedFile = File.from("/is/unrelated/readme.txt")
            val allFiles =
                listOf(originAndBaseFile, fileWithExcludedFileExtension, fileWithAllowedFileExtension, unrelatedFile)

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                originAndBaseFile,
                allFiles,
                excludedFileExtensions(excludedFileExtension)
            )

            expectThat(result) {
                originIsRelatedTo(fileWithAllowedFileExtension)
                originIsNotRelatedTo(fileWithExcludedFileExtension)
                originIsNotRelatedTo(unrelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should not relate itself`() {
            val origin = File.from("/is/origin/file.txt")
            val unrelatedFile = File.from("/is/some/unrelated.txt")
            val allFiles = listOf(origin, unrelatedFile)

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                origin,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsNotRelatedTo(origin)
            }
        }

        private fun excludedFileExtensions(vararg fileExtensions: String): SettingsState {
            val settings = SettingsState()
            settings.excludedFileExtensions.addAll(fileExtensions)
            return settings
        }
    }
}