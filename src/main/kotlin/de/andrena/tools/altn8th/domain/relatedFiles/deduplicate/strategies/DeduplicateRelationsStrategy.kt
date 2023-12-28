package de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal interface DeduplicateRelationsStrategy {
    fun deduplicate(prioritizedRelations: PrioritizedRelations): PrioritizedRelations
}

