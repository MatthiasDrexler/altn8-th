package de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType

internal class AllAreRelatedType : RelationType {
    override fun key(): String = "all"
}