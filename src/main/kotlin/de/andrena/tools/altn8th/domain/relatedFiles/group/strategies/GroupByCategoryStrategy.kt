package de.andrena.tools.altn8th.domain.relatedFiles.group.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

internal class GroupByCategoryStrategy : GroupRelationsStrategy {
    override fun group(relations: Collection<Relation>): List<RelationGroup> =
        relations
            .groupBy { it.type.category() }
            .map { RelationGroup(it.key, it.value) }
}
