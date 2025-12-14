package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups.SortRelationGroupsStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.relations.SortRelationsStrategy

class OrderRelationGroups(
    private val relatedFilePrioritizationStrategy: SortRelationsStrategy,
    private val categoryOrderStrategy: SortRelationGroupsStrategy
) {
    fun arrange(relationGroups: Collection<RelationGroup>): List<RelationGroup> {
        val relationGroupsOrderedByCategory = categoryOrderStrategy.sort(relationGroups)
        return relationGroupsOrderedByCategory.map {
            RelationGroup(
                it.category,
                relatedFilePrioritizationStrategy.prioritize(it.relations)
            )
        }
    }
}
