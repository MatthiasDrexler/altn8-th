package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File

internal data class Relation(val relatedFile: File, val origin: File, val type: RelationType)
