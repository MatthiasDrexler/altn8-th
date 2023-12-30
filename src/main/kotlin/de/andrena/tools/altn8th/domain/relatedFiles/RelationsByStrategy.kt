package de.andrena.tools.altn8th.domain.relatedFiles

internal data class RelationsByStrategy(
    val source: String,
    val relations: Collection<Relation>
)
