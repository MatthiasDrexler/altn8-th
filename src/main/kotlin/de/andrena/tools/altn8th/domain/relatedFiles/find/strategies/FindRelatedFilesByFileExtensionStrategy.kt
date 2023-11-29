package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.SettingsState
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelatedFile
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelationType
import de.andrena.tools.altn8th.domain.relatedFiles.find.toRelatedFile

internal class FindRelatedFilesByFileExtensionStrategy : FindRelatedFilesStrategy {
    override fun findRelatedFiles(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): List<RelatedFile> {
        return allFiles
            .filter(sameFileNameAs(origin))
            .filter(isNot(origin))
            .filter(isFileExtensionNotExcluded(settings))
            .map(toRelatedFile(RelationType.FILE_EXTENSION))
    }

    private fun sameFileNameAs(origin: File) =
        { file: File -> file.nameWithoutFileExtension() == origin.nameWithoutFileExtension() }

    private fun isFileExtensionNotExcluded(settings: SettingsState) =
        { relatedFile: File -> !settings.excludedFileExtensions.contains(relatedFile.fileExtension()) }

    private fun isNot(origin: File): (File) -> Boolean =
        { fileWithEqualName: File -> fileWithEqualName != origin }
}