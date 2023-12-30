package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations


internal class PrioritizeRelationsByFlattening : PrioritizeRelationsStrategy {
    override fun prioritize(unprioritizedRelationsByStrategy: Collection<RelationsByStrategy>): PrioritizedRelations {
        var relations = mutableListOf<Relation>()

        for (relationByType in unprioritizedRelationsByStrategy) {
            for (relation in relationByType.relations) {
                relations.addAll(relationByType.relations.map { it })
            }
        }

        return PrioritizedRelations(relations)
    }
}
