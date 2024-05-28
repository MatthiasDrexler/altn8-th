package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

internal class FindRelatedFilesByPostfixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): Collection<Relation> {
        val baseNameToPostfixSettings = BaseName(origin).regardingTo(settings.postfixes)
        return baseNameToPostfixSettings.map { (basename, originHop) ->
            allFiles.map { relatedFile ->
                settings.postfixes.mapNotNull { relatedFileHop ->
                    if (areNotIdentical(origin, relatedFile)
                        && areRelated(basename, relatedFile.nameWithoutFileExtension(), relatedFileHop)
                    ) {
                        Relation(relatedFile, origin, PostfixRelationType(originHop, relatedFileHop))
                    } else {
                        null
                    }
                }
            }
        }
            .flatten()
            .flatten()
    }

    private fun areNotIdentical(origin: File, relatedFile: File): Boolean = origin != relatedFile

    private fun areRelated(basename: String, relatedFile: String, relatedFileHop: PostfixSetting) =
        areRelatedByGivenPattern(basename, relatedFile, relatedFileHop.pattern)
            || areRelatedByGivenPattern(relatedFile, basename, relatedFileHop.pattern)

    private fun areRelatedByGivenPattern(first: String, second: String, postfixPattern: String): Boolean =
        first.matches(Regex("^${second}(${postfixPattern})$"))
}
