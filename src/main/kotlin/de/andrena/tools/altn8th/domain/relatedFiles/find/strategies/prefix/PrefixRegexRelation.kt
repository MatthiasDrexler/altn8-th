package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

data class PrefixRegexRelation(
    override val relatedFile: File,
    override val origin: File,
    override val explanation: String,
    override val category: String
) : Relation {
    companion object {
        fun from(
            relatedFile: File,
            origin: File,
            originHop: PrefixSetting?,
            relatedFileHop: PrefixSetting
        ): PrefixRegexRelation =
            if (originHop == null) {
                directHop(relatedFile, origin, relatedFileHop)
            } else transitiveHop(relatedFile, origin, originHop, relatedFileHop)

        private fun transitiveHop(
            relatedFile: File,
            origin: File,
            originHop: PrefixSetting,
            relatedFileHop: PrefixSetting
        ): PrefixRegexRelation =
            PrefixRegexRelation(
                relatedFile,
                origin,
                "This file is related to the open file transitively because of the prefix patterns ${originHop.pattern} and ${relatedFileHop.pattern}",
                relatedFileHop.category
            )

        private fun directHop(
            relatedFile: File,
            origin: File,
            relatedFileHop: PrefixSetting
        ): PrefixRegexRelation =
            PrefixRegexRelation(
                relatedFile,
                origin,
                "This file is related to the open file because of the prefix pattern ${relatedFileHop.pattern}",
                relatedFileHop.category
            )
    }

}
