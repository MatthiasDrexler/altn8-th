package de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class DeduplicateRelationsByTakingFirstOccurrence: DeduplicateRelationsStrategy {
    override fun deduplicate(prioritizedRelations: PrioritizedRelations): PrioritizedRelations =
        PrioritizedRelations(prioritizedRelations.relations.distinct())
}
