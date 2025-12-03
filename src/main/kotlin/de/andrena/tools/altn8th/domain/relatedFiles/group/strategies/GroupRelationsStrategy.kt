package de.andrena.tools.altn8th.domain.relatedFiles.group.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

interface GroupRelationsStrategy {
    fun group(relations: Collection<Relation>): Collection<RelationGroup>
}
