package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

internal class FindRelatedFilesByPostfixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        file: File,
        settings: SettingsState
    ): Relation? {
        val baseNameToPostfixSettings = Depostfixer(origin).regardingTo(settings.postfixes)
        val relationType = baseNameToPostfixSettings.mapNotNull { (basename, originHop) ->
            val relatedFileHop = settings.postfixes.firstOrNull { relatedFileHop ->
                areNotIdentical(origin, file)
                    && areRelated(basename, file.nameWithoutFileExtension(), relatedFileHop)
            }
            relatedFileHop?.let { PostfixRelationType(originHop, it) }
        }.firstOrNull()
        return relationType?.let { Relation(file, origin, it) }
    }

    private fun areNotIdentical(origin: File, relatedFile: File): Boolean = origin != relatedFile

    private fun areRelated(basename: String, relatedFile: String, relatedFileHop: PostfixSetting) =
        areRelatedByGivenPattern(basename, relatedFile, relatedFileHop.pattern)
            || areRelatedByGivenPattern(relatedFile, basename, relatedFileHop.pattern)

    private fun areRelatedByGivenPattern(first: String, second: String, postfixPattern: String): Boolean =
        first.matches(Regex("^${second}(${postfixPattern})$"))
}
