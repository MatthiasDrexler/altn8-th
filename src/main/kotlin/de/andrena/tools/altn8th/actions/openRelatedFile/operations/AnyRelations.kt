package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType

internal class AnyRelations(
    private val relations: List<RelationsByType>
) {
    fun areFound(): Boolean = relations
        .flatMap { it.relatedFiles }
        .isNotEmpty()
}
