package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.originIsOnlyRelatedTo
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRelationSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat

@RunWith(Enclosed::class)
class FindRelatedFilesByFreeRelationsStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files by free relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val unrelatedFile = File.from("/is/unrelated/unrelated")

            val result = FindRelatedFilesByFreeRelationsStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRelations(FreeRelationSetting("source", "destination"))
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

            val result = FindRelatedFilesByFreeRelationsStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRelations(FreeRelationSetting("source", "destination[\\w]*"))
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

            val result = FindRelatedFilesByFreeRelationsStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRelations(FreeRelationSetting("source[\\w]*", "destination"))
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

            val result = FindRelatedFilesByFreeRelationsStrategy().find(
                originFile,
                listOf(originFile, relatedFile, unrelatedFile),
                configuredFreeRelations(FreeRelationSetting("source[\\w]*", "destination[\\w]*"))
            )

            expectThat(result) {
                originIsOnlyRelatedTo(relatedFile)
            }
        }

        private fun configuredFreeRelations(vararg freeRelations: FreeRelationSetting): SettingsState {
            val settings = SettingsState()
            settings.freeRelations.clear()
            settings.freeRelations.addAll(freeRelations)
            return settings
        }
    }
}
