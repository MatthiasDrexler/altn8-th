package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup


internal interface SortRelationGroupsStrategy {
    fun sort(relationGroups: Collection<RelationGroup>): List<RelationGroup>
}
