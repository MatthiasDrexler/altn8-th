package de.andrena.tools.altn8th.domain.relatedFiles.prioritize

import de.andrena.tools.altn8th.domain.relatedFiles.Relation

internal data class PrioritizedRelations(val relations: Collection<Relation>) {
    fun onlyOneChoice(): Boolean = relations.size == 1

    fun onlyChoice(): Relation {
        if (!onlyOneChoice()) {
            throw IllegalStateException("There are ${relations.size} choices")
        }

        return relations.first()
    }
}
