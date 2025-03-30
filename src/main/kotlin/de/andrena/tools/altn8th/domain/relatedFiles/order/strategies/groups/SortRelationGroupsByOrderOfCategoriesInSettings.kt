package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class SortRelationGroupsByOrderOfCategoriesInSettings(
    private val state: SettingsState
) : SortRelationGroupsStrategy {
    override fun sort(relationGroups: Collection<RelationGroup>): List<RelationGroup> {
        val typeOccurrences = relationGroups
            .flatMap { group -> group.relations }
            .groupBy { it.type.name() }
            .mapValues { it.value.size }
        val mostFrequentTypeName = typeOccurrences.maxByOrNull { it.value }?.key

        val configuredOrderBySetting = when {
            mostFrequentTypeName?.contains("Prefix", ignoreCase = true) == true ->
                state.prefixes.map { it.category }.distinct()

            mostFrequentTypeName?.contains("FreeRegex", ignoreCase = true) == true ->
                state.freeRegexes.map { it.category }.distinct()

            else -> state.postfixes.map { it.category }.distinct()
        }

        return relationGroups.sortedWith(compareBy { relationGroup ->
            val index = configuredOrderBySetting.indexOf(relationGroup.category)
            if (index != -1) index else configuredOrderBySetting.size
        })
    }
}
