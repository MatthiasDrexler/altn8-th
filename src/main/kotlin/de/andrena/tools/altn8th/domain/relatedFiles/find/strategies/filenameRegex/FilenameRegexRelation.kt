package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting

data class FilenameRegexRelation(
    override val relatedFile: File,
    override val origin: File,
    override val explanation: String,
    override val category: String
) : Relation {
    companion object {
        fun from(relatedFile: File, origin: File, matchedBy: FilenameRegexSetting) =
            FilenameRegexRelation(
                relatedFile,
                origin,
                "This file is related to the open file because of the filename regex '${matchedBy.origin}' -> ${matchedBy.related}",
                matchedBy.category
            )
    }
}
