package de.andrena.tools.altn8th.domain.relatedFiles.find

import de.andrena.tools.altn8th.domain.File

data class RelatedFile(val file: File, val findingType: RelationType) {
    override fun toString(): String {
        return "RelatedFile(file=$file, findingType=$findingType)"
    }
}
