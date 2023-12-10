package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File

internal data class Relation(val type: RelationType, val origin: File, val relatedFile: File)
