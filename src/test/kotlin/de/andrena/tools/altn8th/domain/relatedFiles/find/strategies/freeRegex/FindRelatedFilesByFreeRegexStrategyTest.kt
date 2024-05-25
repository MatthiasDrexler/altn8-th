package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.originIsOnlyRelatedTo
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat

@RunWith(Enclosed::class)
class FindRelatedFilesByFreeRegexStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files by free relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val unrelatedFile = File.from("/is/unrelated/unrelated")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRegexes(FreeRegexSetting("source", "destination", "category"))
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile)
            }
        }

        @Test
        fun `should relate files by free relations for regex destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val unrelatedFile = File.from("/is/unrelated/unrelated")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRegexes(FreeRegexSetting("source", "destination[\\w]*", "category"))
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile)
            }
        }

        @Test
        fun `should relate files by free relations for regex origin`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destination")
            val unrelatedFile = File.from("/is/unrelated/unrelated")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRegexes(FreeRegexSetting("source[\\w]*", "destination", "category"))
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile)
            }
        }

        @Test
        fun `should relate files by free relations for regex origin and destination`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val unrelatedFile = File.from("/is/unrelated/unrelated")

            val result = FindRelatedFilesByFreeRegexStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRegexes(FreeRegexSetting("source[\\w]*", "destination[\\w]*", "category"))
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile)
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
