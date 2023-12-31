package de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType

internal class AllAreRelatedType : RelationType {
    override fun name() = "all"
    override fun explanation() = "All files are considered related (for testing purposes)"
    override fun category() = name()
}
