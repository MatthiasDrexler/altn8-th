package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class FindRelatedFilesByFileExtensionStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByType {
        val relatedFiles = allFiles
            .filter(sameFileNameAs(origin))
            .filter(isNot(origin))
            .filter(isFileExtensionNotExcluded(settings))
        return RelationsByType(FileExtensionRelationType(), origin, relatedFiles)
    }

    private fun sameFileNameAs(origin: File) =
        { file: File -> file.nameWithoutFileExtension() == origin.nameWithoutFileExtension() }

    private fun isFileExtensionNotExcluded(settings: SettingsState) =
        { relatedFile: File -> !settings.excludedFileExtensions.contains(relatedFile.fileExtension()) }

    private fun isNot(origin: File): (File) -> Boolean =
        { fileWithEqualName: File -> fileWithEqualName != origin }
}