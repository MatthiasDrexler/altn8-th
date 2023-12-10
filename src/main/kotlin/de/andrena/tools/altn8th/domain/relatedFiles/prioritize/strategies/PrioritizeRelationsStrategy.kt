package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations


internal interface PrioritizeRelationsStrategy {
    fun prioritize(unprioritizedRelationsByType: Collection<RelationsByType>): PrioritizedRelations
}
