package de.andrena.tools.altn8th.actions.goToRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.group.strategies.GroupRelationsStrategy

class GroupRelations(private val relations: Collection<Relation>, private val strategy: GroupRelationsStrategy) {
    fun group(): Collection<RelationGroup> = strategy.group(relations)
}
