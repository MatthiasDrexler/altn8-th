package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations


internal class PrioritizeRelationsByFlattening : PrioritizeRelationsStrategy {
    override fun prioritize(unprioritizedRelationsByStrategy: Collection<Relation>): PrioritizedRelations {
        return PrioritizedRelations(unprioritizedRelationsByStrategy.toList())
    }
}
