package de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation


internal interface PrioritizeRelationsStrategy {
    fun prioritize(relations: Collection<Relation>): List<Relation>
}
