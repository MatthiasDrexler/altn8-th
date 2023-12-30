package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy

internal class AnyRelations(
    private val relations: List<RelationsByStrategy>
) {
    fun areFound(): Boolean = relations
        .flatMap { it.relations }
        .isNotEmpty()
}
