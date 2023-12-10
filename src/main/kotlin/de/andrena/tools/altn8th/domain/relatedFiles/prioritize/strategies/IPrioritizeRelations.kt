package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType


internal interface IPrioritizeRelations {
    fun prioritize(unprioritizedRelationsByType: Collection<RelationsByType>): List<RelationsByType>
}
