package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(Enclosed::class)
class FindRelatedFilesByFreeRegexStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files by free relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val freeRegexSetting = FreeRegexSetting("source", "destination", "category")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFreeRegexes(freeRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FreeRegexRelationType(freeRegexSetting)))
            }
        }

        @Test
        fun `should relate files case-insensitively by free relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val freeRegexSetting = FreeRegexSetting("Source", "Destination", "Category")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFreeRegexes(freeRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FreeRegexRelationType(freeRegexSetting)))
            }
        }

        @Test
        fun `should relate files by free relations for regex destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val freeRegexSetting = FreeRegexSetting("source", "destination[\\w]*", "category")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFreeRegexes(freeRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FreeRegexRelationType(freeRegexSetting)))
            }
        }

        @Test
        fun `should relate files by free relations for regex origin`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destination")
            val freeRegexSetting = FreeRegexSetting("source[\\w]*", "destination", "category")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFreeRegexes(freeRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FreeRegexRelationType(freeRegexSetting)))
            }
        }

        @Test
        fun `should relate files by free relations for regex origin and destination`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val freeRegexSetting = FreeRegexSetting("source[\\w]*", "destination[\\w]*", "category")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFreeRegexes(freeRegexSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, originFile, FreeRegexRelationType(freeRegexSetting)))
            }
        }

        private fun configuredFreeRegexes(vararg freeRegexes: FreeRegexSetting): SettingsState {
            val settings = SettingsState()
            settings.freeRegexes.clear()
            settings.freeRegexes.addAll(freeRegexes)
            return settings
        }
    }
}
