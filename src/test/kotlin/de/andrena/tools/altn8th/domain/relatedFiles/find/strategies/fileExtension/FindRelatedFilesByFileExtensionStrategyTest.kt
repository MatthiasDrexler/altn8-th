package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.SettingsState
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@RunWith(Enclosed::class)
class FindRelatedFilesByFileExtensionStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files with same name and different allowed extension`() {
            val originAndBaseFile = File.from("/is/origin/angular.component.ts")
            val relatedFile = File.from("/is/related/${originAndBaseFile.nameWithoutFileExtension()}.css")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                relatedFile,
                excludedFileExtensions()
            )

            expectThat(result) {
                isEqualTo(SameFilenameRelation(relatedFile, originAndBaseFile))
            }
        }

        @Test
        fun `should handle filenames with special regex characters`() {
            val originAndBaseFile = File.from("/is/origin/[angular].component.ts")
            val relatedFile = File.from("/is/related/[angular].component.css")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                relatedFile,
                excludedFileExtensions()
            )

            expectThat(result) {
                isEqualTo(SameFilenameRelation(relatedFile, originAndBaseFile))
            }
        }

        @Test
        fun `should relate files case-insensitively with same name and different allowed extension`() {
            val originAndBaseFile = File.from("/is/origin/base.txt")
            val relatedFile = File.from("/is/related/Base.md")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                relatedFile,
                excludedFileExtensions()
            )

            expectThat(result) {
                isEqualTo(SameFilenameRelation(relatedFile, originAndBaseFile))
            }
        }

        @Test
        fun `should relate files with same name and extension at different path`() {
            val originAndBaseFile = File.from("/is/origin/file.kt")
            val relatedAtAnotherPath = File.from("/is/related/${originAndBaseFile.nameWithFileExtension()}")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                relatedAtAnotherPath,
                excludedFileExtensions()
            )

            expectThat(result) {
                isEqualTo(SameFilenameRelation(relatedAtAnotherPath, originAndBaseFile))
            }
        }

        @Test
        fun `should relate files with allowed extension from origin with excluded extension`() {
            val allowedFileExtension = "ts"
            val excludedFileExtension = "js"

            val originAndBaseFile = File.from("/is/origin/file.${excludedFileExtension}")
            val relatedFile =
                File.from("/is/related/${originAndBaseFile.nameWithoutFileExtension()}.${allowedFileExtension}")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                relatedFile,
                excludedFileExtensions()
            )

            expectThat(result) {
                isEqualTo(SameFilenameRelation(relatedFile, originAndBaseFile))
            }
        }

        @Test
        fun `should not relate files with excluded file extension`() {
            val excludedFileExtension = "js"

            val originAndBaseFile = File.from("/is/origin/file.txt")
            val fileWithExcludedFileExtension =
                File.from("/is/unrelated/${originAndBaseFile.nameWithoutFileExtension()}.${excludedFileExtension}")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                originAndBaseFile,
                fileWithExcludedFileExtension,
                excludedFileExtensions(excludedFileExtension)
            )

            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not relate itself`() {
            val origin = File.from("/is/origin/file.txt")

            val result = FindRelatedFilesByFileExtensionStrategy().find(
                origin,
                origin,
                excludedFileExtensions()
            )

            expectThat(result) {
                isNull()
            }
        }

        private fun excludedFileExtensions(vararg fileExtensions: String): SettingsState {
            val settings = SettingsState()
            settings.excludedFileExtensions.addAll(fileExtensions)
            return settings
        }
    }
}
