package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType


internal class PrioritizeRelationsByFlattening : IPrioritizeRelations {
    override fun prioritize(unprioritizedRelationsByType: Collection<RelationsByType>): List<RelationsByType> =
        unprioritizedRelationsByType.map { it }
}
