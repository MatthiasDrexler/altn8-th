package de.andrena.tools.altn8th.actions.goToRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies.DeduplicateRelationsStrategy

class DeduplicateRelations(
    private val prioritizedRelations: Collection<Relation>,
    private val deduplicationStrategy: DeduplicateRelationsStrategy
) {
    fun deduplicate(): Collection<Relation> = deduplicationStrategy.deduplicate(prioritizedRelations)
}
