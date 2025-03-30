package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.containsExactly

@RunWith(Enclosed::class)
class SortRelationGroupsByOrderOfCategoriesInSettingsTest {
    class Sort {
        @Test
        fun `should sort by the order of categories in settings and take the setting with the most entries`() {
            val settings = SettingsState()
            settings.postfixes.add(PostfixSetting("postfix", "postfix", "A"))
            settings.postfixes.add(PostfixSetting("postfix", "postfix", "B"))
            settings.postfixes.add(PostfixSetting("postfix", "postfix", "C"))
            settings.prefixes.add(PrefixSetting("prefix", "prefix", "B"))
            settings.prefixes.add(PrefixSetting("prefix", "prefix", "A"))
            settings.freeRegexes.add(FreeRegexSetting("regex", "regex", "B"))
            settings.freeRegexes.add(FreeRegexSetting("regex", "regex", "A"))

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
            val settings = SettingsState()
            settings.postfixes.add(PostfixSetting("postfix", "postfix", "A"))

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
        fun `should alwys put the file extension at the end`() {
            val settings = SettingsState()
            settings.postfixes.add(PostfixSetting("postfix", "postfix", "A"))
            settings.postfixes.add(PostfixSetting("postfix", "postfix", "B"))

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
