package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation


internal class PrioritizeRelationsByFlattening : PrioritizeRelationsStrategy {
    override fun prioritize(relations: Collection<Relation>): List<Relation> {
        return relations.toList()
    }
}
