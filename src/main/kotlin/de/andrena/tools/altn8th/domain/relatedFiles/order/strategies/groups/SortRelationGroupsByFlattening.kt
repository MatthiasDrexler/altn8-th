package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup


internal class SortRelationGroupsByFlattening : SortRelationGroupsStrategy {
    override fun sort(relationGroups: Collection<RelationGroup>): List<RelationGroup> {
        return relationGroups.toList()
    }
}
