package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy

internal data class RelationsByStrategy(
    val source: FindRelatedFilesStrategy,
    val relations: Collection<Relation>
)
