package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class SortRelationGroupsByOrderOfCategoriesInSettings(
    private val state: SettingsState
) : SortRelationGroupsStrategy {
    override fun sort(relationGroups: Collection<RelationGroup>): List<RelationGroup> {
        val configuredOrderBySetting = listOf(
            state.postfixes.map { it.category },
            state.prefixes.map { it.category },
            state.freeRegexes.map { it.category }
        )
            .maxBy { it.size }
            .distinct()

        return relationGroups.sortedWith(compareBy { relationGroup ->
            val index = configuredOrderBySetting.indexOf(relationGroup.category)
            if (index != -1) index else configuredOrderBySetting.size
        })
    }
}
