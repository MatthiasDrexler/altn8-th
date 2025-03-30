package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.relations

import de.andrena.tools.altn8th.domain.relatedFiles.Relation


internal class SortRelationsByFlattening : SortRelationsStrategy {
    override fun prioritize(relations: Collection<Relation>): List<Relation> =
        relations.toList()
}
