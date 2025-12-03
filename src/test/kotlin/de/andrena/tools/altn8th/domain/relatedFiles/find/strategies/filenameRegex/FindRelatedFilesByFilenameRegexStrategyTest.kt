package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(Enclosed::class)
class FindRelatedFilesByFilenameRegexStrategyTest {
    class FindRelatedFiles {
        val caseInsensitive = true
        val caseSensitive = false

        @Test
        fun `should relate files by filename relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filenameRegexSetting = FilenameRegexSetting("source", "destination", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FilenameRegexRelationType(filenameRegexSetting)))
            }
        }

        @Test
        fun `should handle filenames with special regex characters`() {
            val originFile = File.from("/is/origin/[source]")
            val relatedFile = File.from("/is/related/[destination]")
            val filenameRegexSetting = FilenameRegexSetting("\\[source\\]", "\\[destination\\]", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FilenameRegexRelationType(filenameRegexSetting)))
            }
        }

        @Test
        fun `should relate files case-insensitively by filename relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filenameRegexSetting = FilenameRegexSetting("Source", "Destination", "Category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseInsensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FilenameRegexRelationType(filenameRegexSetting)))
            }
        }

        @Test
        fun `should relate files by filename relations for regex destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filenameRegexSetting = FilenameRegexSetting("source", "destination[\\w]*", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FilenameRegexRelationType(filenameRegexSetting)))
            }
        }

        @Test
        fun `should relate files by filename relations for regex origin`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destination")
            val filenameRegexSetting = FilenameRegexSetting("source[\\w]*", "destination", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FilenameRegexRelationType(filenameRegexSetting)))
            }
        }

        @Test
        fun `should relate files by filename relations for regex origin and destination`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filenameRegexSetting = FilenameRegexSetting("source[\\w]*", "destination[\\w]*", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FilenameRegexRelationType(filenameRegexSetting)))
            }
        }

        private fun configuredFilenameRegexes(
            caseInsensitive: Boolean,
            vararg filenameRegexes: FilenameRegexSetting
        ): SettingsState {
            val settings = SettingsState()
            settings.caseInsensitiveMatching = caseInsensitive
            settings.filenameRegexes.clear()
            settings.filenameRegexes.addAll(filenameRegexes)
            return settings
        }
    }
}
