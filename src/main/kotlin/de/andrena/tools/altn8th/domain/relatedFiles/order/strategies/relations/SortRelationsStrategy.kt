package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.relations

import de.andrena.tools.altn8th.domain.relatedFiles.Relation


interface SortRelationsStrategy {
    fun prioritize(relations: Collection<Relation>): List<Relation>
}
