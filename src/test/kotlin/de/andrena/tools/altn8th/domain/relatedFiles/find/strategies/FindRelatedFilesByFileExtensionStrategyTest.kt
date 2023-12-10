package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FileExtensionRelationType
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsOnlyRelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsRelatedBy
import de.andrena.tools.altn8th.domain.relatedFiles.originIsUnrelatedTo
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

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                listOf(originAndBaseFile, relatedFile, unrelatedFile, anotherRelatedFile),
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile, anotherRelatedFile)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should relate files with same name and extension at different path`() {
            val originAndBaseFile = File.from("/is/origin/file.kt")
            val relatedAtAnotherPath = File.from("/is/related/${originAndBaseFile.nameWithFileExtension()}")
            val unrelatedFile = File.from("/is/unrelated.ts")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                listOf(originAndBaseFile, unrelatedFile, relatedAtAnotherPath),
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedAtAnotherPath)
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

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                listOf(originAndBaseFile, relatedFile, unrelatedFile),
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile)
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

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                listOf(originAndBaseFile, fileWithExcludedFileExtension, fileWithAllowedFileExtension, unrelatedFile),
                excludedFileExtensions(excludedFileExtension)
            )

            expectThat(result) {
                originIsOnlyRelatedTo(fileWithAllowedFileExtension)
                originIsRelatedBy(FileExtensionRelationType())
            }
        }

        @Test
        fun `should not relate itself`() {
            val origin = File.from("/is/origin/file.txt")
            val unrelatedFile = File.from("/is/some/unrelated.txt")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                origin,
                listOf(origin, unrelatedFile),
                excludedFileExtensions()
            )

            expectThat(result) {
                originIsUnrelatedTo(origin)
            }
        }

        private fun excludedFileExtensions(vararg fileExtensions: String): SettingsState {
            val settings = SettingsState()
            settings.excludedFileExtensions.addAll(fileExtensions)
            return settings
        }
    }
}
