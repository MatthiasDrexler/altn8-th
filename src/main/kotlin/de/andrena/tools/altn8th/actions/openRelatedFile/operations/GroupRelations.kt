package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.group.strategies.GroupRelationsStrategy

internal class GroupRelations(private val relations: List<Relation>, private val strategy: GroupRelationsStrategy) {
    fun group(): List<RelationGroup> = strategy.group(relations)
}
