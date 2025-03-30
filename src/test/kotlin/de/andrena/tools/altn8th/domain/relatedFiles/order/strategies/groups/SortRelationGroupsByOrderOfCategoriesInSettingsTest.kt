package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.settings.buildCleanSettingsState
import de.andrena.tools.altn8th.settings.provideFreeRegexSettingsWithCategories
import de.andrena.tools.altn8th.settings.providePostfixSettingsWithCategories
import de.andrena.tools.altn8th.settings.providePrefixSettingsWithCategories
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.containsExactly

@RunWith(Enclosed::class)
class SortRelationGroupsByOrderOfCategoriesInSettingsTest {
    class Sort {
        @Test
        fun `should sort by the order of categories in settings and take the setting with the most distinct entries`() {
            val settings = buildCleanSettingsState()
            settings.providePrefixSettingsWithCategories("A", "B", "C")
            settings.providePostfixSettingsWithCategories("B", "A", "A", "A")
            settings.provideFreeRegexSettingsWithCategories("B", "A")

            val result = SortRelationGroupsByOrderOfCategoriesInSettings(settings).sort(
                listOf(
                    RelationGroup("B", listOf()),
                    RelationGroup("A", listOf()),
                )
            )

            expectThat(result) {
                containsExactly(
                    RelationGroup("A", listOf()),
                    RelationGroup("B", listOf())
                )
            }
        }

        @Test
        fun `should put unknown categories at the end`() {
            val settings = buildCleanSettingsState()
            settings.providePrefixSettingsWithCategories("A")

            val result = SortRelationGroupsByOrderOfCategoriesInSettings(settings).sort(
                listOf(
                    RelationGroup("C", listOf()),
                    RelationGroup("B", listOf()),
                    RelationGroup("A", listOf()),
                )
            )

            expectThat(result) {
                containsExactly(
                    RelationGroup("A", listOf()),
                    RelationGroup("C", listOf()),
                    RelationGroup("B", listOf()),
                )
            }
        }

        @Test
        fun `should always put the file extension at the end`() {
            val settings = buildCleanSettingsState()
            settings.providePrefixSettingsWithCategories("A", "B")

            val result = SortRelationGroupsByOrderOfCategoriesInSettings(settings).sort(
                listOf(
                    RelationGroup("Same Filename", listOf()),
                    RelationGroup("A", listOf()),
                )
            )

            expectThat(result) {
                containsExactly(
                    RelationGroup("A", listOf()),
                    RelationGroup("Same Filename", listOf()),
                )
            }
        }
    }
}
