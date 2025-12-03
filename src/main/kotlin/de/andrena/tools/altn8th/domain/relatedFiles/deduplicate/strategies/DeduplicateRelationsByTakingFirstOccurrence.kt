package de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.Relation

class DeduplicateRelationsByTakingFirstOccurrence: DeduplicateRelationsStrategy {
    override fun deduplicate(relations: Collection<Relation>): Collection<Relation> =
        relations.distinct()
}
