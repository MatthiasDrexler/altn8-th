package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filePath

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilePathRegexSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(Enclosed::class)
class FindRelatedFilesByFilePathRegexStrategyTest {
    class FindRelatedFiles {
        val caseInsensitive = true
        val caseSensitive = false

        @Test
        fun `should relate files by filename relations for fixed destination without escaping`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source",
                "/is/related/destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by filename relations for fixed destination with escaping`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "\\/is\\/origin\\/source",
                "\\/is\\/related\\/destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle file paths with special regex characters`() {
            val originFile = File.from("/is/[origin]/source")
            val relatedFile = File.from("/is/[related]/destination")
            val filenameRegexSetting =
                FilePathRegexSetting("\\/is\\/\\[origin\\]\\/source", "\\/is\\/\\[related\\]\\/destination", "category")

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files case-insensitively by file path relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/Source",
                "/is/related/Destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseInsensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by file path relations for regex destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source",
                "/is/related/destination[\\w]*",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by file path relations for regex origin`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source[\\w]*",
                "/is/related/destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by file path relations for regex origin and destination`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source[\\w]*",
                "/is/related/destination[\\w]*",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        private fun configuredFilePathRegexes(
            caseInsensitive: Boolean,
            vararg filePathRegexes: FilePathRegexSetting
        ): SettingsState {
            val settings = SettingsState()
            settings.caseInsensitiveMatching = caseInsensitive
            settings.filePathRegexes.clear()
            settings.filePathRegexes.addAll(filePathRegexes)
            return settings
        }
    }
}
