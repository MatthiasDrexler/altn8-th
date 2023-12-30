package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations


internal interface PrioritizeRelationsStrategy {
    fun prioritize(unprioritizedRelationsByStrategy: Collection<RelationsByStrategy>): PrioritizedRelations
}
