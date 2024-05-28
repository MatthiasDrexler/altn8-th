package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class FindRelatedFilesByFileExtensionStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByStrategy {
        val relatedFiles = allFiles
            .filter(sameFilenameAs(origin))
            .filter(isNot(origin))
            .filter(isFileExtensionNotExcluded(settings))
            .map { Relation(it, origin, FileExtensionRelationType()) }
        return RelationsByStrategy(relatedFiles)
    }

    private fun sameFilenameAs(origin: File) =
        { file: File -> file.nameWithoutFileExtension() == origin.nameWithoutFileExtension() }

    private fun isFileExtensionNotExcluded(settings: SettingsState) =
        { relatedFile: File -> !settings.excludedFileExtensions.contains(relatedFile.fileExtension()) }

    private fun isNot(origin: File) = { relatedFile: File -> relatedFile != origin }
}
