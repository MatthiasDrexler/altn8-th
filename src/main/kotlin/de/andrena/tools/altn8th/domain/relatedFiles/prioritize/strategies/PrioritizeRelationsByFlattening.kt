package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations


internal class PrioritizeRelationsByFlattening : PrioritizeRelationsStrategy {
    override fun prioritize(unprioritizedRelationsByType: Collection<RelationsByType>): PrioritizedRelations {
        var relations = mutableListOf<Relation>()

        for (relationByType in unprioritizedRelationsByType) {
            for (relation in relationByType.relatedFiles) {
                relations.addAll(relationByType.relatedFiles.map {
                    Relation(
                        relationByType.type,
                        relationByType.origin,
                        relation
                    )
                })
            }
        }

        return PrioritizedRelations(relations)
    }
}
