package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation

class AllAreRelated(
    override val relatedFile: File,
    override val origin: File,
    override val explanation: String = "All files are considered related (for testing purposes)",
    override val category: String = "all",
) : Relation
