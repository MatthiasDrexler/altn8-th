package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File

internal data class RelationsByType(
    val type: RelationType,
    val origin: File,
    val relatedFiles: Collection<File>
)