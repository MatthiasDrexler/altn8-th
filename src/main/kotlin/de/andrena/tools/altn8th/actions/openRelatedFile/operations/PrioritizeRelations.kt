package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies.PrioritizeRelationsByFlattening

internal class PrioritizeRelations(
    private val relations: Collection<RelationsByType>,
    private val relatedFilePrioritizationStrategy: PrioritizeRelationsByFlattening
) {
    fun prioritize(): List<RelationsByType> = relatedFilePrioritizationStrategy.prioritize(relations)
}
