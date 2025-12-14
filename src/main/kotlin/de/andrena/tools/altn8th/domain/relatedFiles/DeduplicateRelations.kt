package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies.DeduplicateRelationsStrategy

class DeduplicateRelations(private val deduplicationStrategy: DeduplicateRelationsStrategy) {

    fun deduplicate(prioritizedRelations: Collection<Relation>): Collection<Relation> =
        deduplicationStrategy.deduplicate(prioritizedRelations)
}
