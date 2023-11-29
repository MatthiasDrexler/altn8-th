package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelatedFile
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelationType
import de.andrena.tools.altn8th.domain.relatedFiles.find.toRelatedFile
import de.andrena.tools.altn8th.settings.SettingsState

internal class FindRelatedFilesByNamePostfixStrategy : FindRelatedFilesStrategy {
    override fun findRelatedFiles(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): List<RelatedFile> =
        origin.baseNamesFromPostfixes(settings.postfixes)
            .map { baseNameOfOrigin ->
                allFiles
                    .filter(relatedFilesDueToPostfixes(baseNameOfOrigin, settings))
                    .map(toRelatedFile(RelationType.NAME_POSTFIX))
            }
            .flatten()

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
}
