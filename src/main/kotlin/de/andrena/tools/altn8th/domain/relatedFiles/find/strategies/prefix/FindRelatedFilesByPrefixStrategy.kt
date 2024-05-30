package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

internal class FindRelatedFilesByPrefixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        file: File,
        settings: SettingsState
    ): Relation? {
        val baseNameToPrefixSettings = BaseName(origin).regardingTo(settings.prefixes)
        val relationType = baseNameToPrefixSettings.mapNotNull { (basename, originHop) ->
            val relatedFileHop = settings.prefixes.firstOrNull { relatedFileHop ->
                areNotIdentical(origin, file)
                    && areRelated(basename, file.nameWithoutFileExtension(), relatedFileHop)
            }
            relatedFileHop?.let { PrefixRelationType(originHop, it) }
        }.firstOrNull()
        return relationType?.let { Relation(file, origin, it) }
    }

    private fun areNotIdentical(origin: File, relatedFile: File): Boolean = origin != relatedFile

    private fun areRelated(basename: String, relatedFile: String, relatedFileHop: PrefixSetting) =
        areRelatedByGivenPattern(basename, relatedFile, relatedFileHop.pattern)
            || areRelatedByGivenPattern(relatedFile, basename, relatedFileHop.pattern)

    private fun areRelatedByGivenPattern(first: String, second: String, prefixPattern: String): Boolean =
        first.matches(Regex("^(${prefixPattern})${second}$"))
}
