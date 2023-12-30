package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.patterns

internal class FindRelatedFilesByPostfixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByType {
        val relatedFiles = origin.baseNamesFromPostfixes(settings.postfixes.patterns())
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
            settings.postfixes.patterns().any { postfixPattern ->
                relatedFileDueToPostfix(
                    file.nameWithoutFileExtension(), origin, postfixPattern
                ) || relatedFileDueToPostfix(
                    origin, file.nameWithoutFileExtension(), postfixPattern
                )
            }
        }

    private fun relatedFileDueToPostfix(first: String, second: String, postfixPattern: String): Boolean =
        first.matches(Regex("^${second}${postfixPattern}$"))

    private fun isNot(origin: File) = { relatedFile: File -> relatedFile != origin }
}
