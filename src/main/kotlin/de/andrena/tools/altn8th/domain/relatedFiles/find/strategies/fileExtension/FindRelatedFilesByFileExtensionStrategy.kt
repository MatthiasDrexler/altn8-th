package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class FindRelatedFilesByFileExtensionStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        file: File,
        settings: SettingsState
    ): Relation? = if (isRelated(origin, file, settings))
        Relation(
            file,
            origin,
            FileExtensionRelationType()
        ) else null

    private fun isRelated(
        origin: File,
        file: File,
        settings: SettingsState
    ) = sameFilenameAs(origin, file) && isNot(origin, file) && isFileExtensionNotExcluded(file, settings)

    private fun sameFilenameAs(origin: File, file: File) =
        file.nameWithoutFileExtension().equals(origin.nameWithoutFileExtension(), ignoreCase = true)

    private fun isFileExtensionNotExcluded(file: File, settings: SettingsState) =
        !settings.excludedFileExtensions.contains(file.fileExtension())

    private fun isNot(origin: File, file: File) = file != origin
}
