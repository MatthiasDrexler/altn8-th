package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.relatedFiles.group.strategies.GroupRelationsStrategy

class GroupRelations(private val strategy: GroupRelationsStrategy) {

    fun group(relations: Collection<Relation>): Collection<RelationGroup> =
        strategy.group(relations)
}
