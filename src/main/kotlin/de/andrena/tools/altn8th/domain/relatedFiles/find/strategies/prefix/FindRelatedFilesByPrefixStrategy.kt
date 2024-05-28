package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

internal class FindRelatedFilesByPrefixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): Collection<Relation> {
        val baseNameToPrefixSettings = BaseName(origin).regardingTo(settings.prefixes)
        return baseNameToPrefixSettings.flatMap { (basename, originHop) ->
            allFiles.map { relatedFile ->
                settings.prefixes.mapNotNull { relatedFileHop ->
                    if (areNotIdentical(origin, relatedFile)
                        && areRelated(basename, relatedFile.nameWithoutFileExtension(), relatedFileHop)
                    ) {
                        Relation(relatedFile, origin, PrefixRelationType(originHop, relatedFileHop))
                    } else {
                        null
                    }
                }
            }
        }
            .flatten()
    }

    private fun areNotIdentical(origin: File, relatedFile: File): Boolean = origin != relatedFile

    private fun areRelated(basename: String, relatedFile: String, relatedFileHop: PrefixSetting) =
        areRelatedByGivenPattern(basename, relatedFile, relatedFileHop.pattern)
            || areRelatedByGivenPattern(relatedFile, basename, relatedFileHop.pattern)

    private fun areRelatedByGivenPattern(first: String, second: String, prefixPattern: String): Boolean =
        first.matches(Regex("^(${prefixPattern})${second}$"))
}
