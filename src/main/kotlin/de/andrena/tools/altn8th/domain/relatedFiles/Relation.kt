package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File

internal interface Relation {
    val relatedFile: File
    val origin: File
    val explanation: String
    val category: String
}
