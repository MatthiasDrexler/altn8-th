package de.andrena.tools.altn8th.domain.relatedFiles.find

import de.andrena.tools.altn8th.domain.File

internal fun toRelatedFile(relationType: RelationType) =
    { file: File -> RelatedFile(file, relationType) }
