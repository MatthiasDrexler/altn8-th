package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups.SortRelationGroupsStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.relations.SortRelationsStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

class OrderRelationGroups(
    private val relationGroups: Collection<RelationGroup>,
    private val relatedFilePrioritizationStrategy: SortRelationsStrategy,
    private val categoryOrderStrategy: SortRelationGroupsStrategy,
    private val settings: SettingsState
) {
    fun arrange(): List<RelationGroup> {
        val relationGroupsOrderedByCategory = categoryOrderStrategy.sort(relationGroups)
        return relationGroupsOrderedByCategory.map {
            RelationGroup(
                it.category,
                relatedFilePrioritizationStrategy.prioritize(it.relations)
            )
        }
    }
}
