package de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType

internal class NoneAreRelatedType : RelationType {
    override fun key(): String = "none"
}