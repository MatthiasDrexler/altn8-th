package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class FindRelatedFilesByPostfixStrategy : FindRelatedFilesStrategy {
    override fun findRelatedFiles(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByType {
        val relatedFiles = origin.baseNamesFromPostfixes(settings.postfixes)
            .map(findRelatedFilesForEachBaseName(allFiles, settings))
            .flatten()
            .filter(isNot(origin))
        return RelationsByType(PostfixRelationType(), origin, relatedFiles)
    }

    private fun findRelatedFilesForEachBaseName(
        allFiles: Collection<File>,
        settings: SettingsState
    ) =
        { baseNameOfOrigin: String ->
            allFiles.filter(relatedFilesDueToPostfixes(baseNameOfOrigin, settings))
        }

    private fun relatedFilesDueToPostfixes(origin: String, settings: SettingsState) =
        { file: File ->
            settings.postfixes.any { postfix ->
                relatedFileDueToPostfix(
                    file.nameWithoutFileExtension(), origin, postfix
                ) || relatedFileDueToPostfix(
                    origin, file.nameWithoutFileExtension(), postfix
                )
            }
        }

    private fun relatedFileDueToPostfix(first: String, second: String, postfix: String): Boolean =
        first.matches(Regex("^${second}${postfix}$"))

    private fun isNot(origin: File) = { relatedFile: File -> relatedFile != origin }
}
