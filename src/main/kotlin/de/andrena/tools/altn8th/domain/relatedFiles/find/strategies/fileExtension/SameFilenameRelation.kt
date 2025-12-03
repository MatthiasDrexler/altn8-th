package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation

data class SameFilenameRelation(
    override val relatedFile: File,
    override val origin: File,
    override val explanation: String = "This file is related to the open file because they share the same filename",
    override val category: String = "Same Filename"
) : Relation
