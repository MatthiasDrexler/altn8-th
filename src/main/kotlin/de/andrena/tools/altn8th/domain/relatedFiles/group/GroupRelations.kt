package de.andrena.tools.altn8th.domain.relatedFiles.group

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.group.strategies.GroupRelationsStrategy

class GroupRelations(private val strategy: GroupRelationsStrategy) {

    fun group(relations: Collection<Relation>): Collection<RelationGroup> =
        strategy.group(relations)
}
