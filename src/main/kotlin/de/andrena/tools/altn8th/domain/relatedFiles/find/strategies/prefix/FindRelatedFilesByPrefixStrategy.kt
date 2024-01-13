package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

internal class FindRelatedFilesByPrefixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByStrategy {
        val baseNameToPrefixSettings = BaseName(origin).regardingTo(settings.prefixes)
        val relations = baseNameToPrefixSettings.map { (basename, originHop) ->
            allFiles.map { relatedFile ->
                settings.prefixes.mapNotNull { relatedFileHop ->
                    if (areNotIdentical(origin, relatedFile)
                        && areRelated(basename, relatedFile.nameWithoutFileExtension(), relatedFileHop)
                    ) {
                        Relation(origin, relatedFile, PrefixRelationType(originHop, relatedFileHop))
                    } else {
                        null
                    }
                }
            }
        }
            .flatten()
            .flatten()

        return RelationsByStrategy(this, relations)
    }

    private fun areNotIdentical(origin: File, relatedFile: File): Boolean = origin != relatedFile

    private fun areRelated(basename: String, relatedFile: String, relatedFileHop: PrefixSetting) =
        areRelatedByGivenPattern(basename, relatedFile, relatedFileHop.pattern)
            || areRelatedByGivenPattern(relatedFile, basename, relatedFileHop.pattern)

    private fun areRelatedByGivenPattern(first: String, second: String, prefixPattern: String): Boolean =
        first.matches(Regex("^${prefixPattern}${second}$"))
}
