package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies.DeduplicateRelationsStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class DeduplicateRelations(
    private val prioritizedRelations: PrioritizedRelations,
    private val deduplicationStrategy: DeduplicateRelationsStrategy
) {
    fun deduplicate(): PrioritizedRelations = deduplicationStrategy.deduplicate(prioritizedRelations)
}
