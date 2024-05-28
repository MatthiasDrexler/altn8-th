package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies.PrioritizeRelationsStrategy

internal class PrioritizeRelations(
    private val relations: Collection<Relation>,
    private val relatedFilePrioritizationStrategy: PrioritizeRelationsStrategy
) {
    fun prioritize(): PrioritizedRelations = relatedFilePrioritizationStrategy.prioritize(relations)
}
