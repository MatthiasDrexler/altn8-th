package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelatedFile
import de.andrena.tools.altn8th.settings.SettingsState

internal interface FindRelatedFilesStrategy {
    fun findRelatedFiles(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): List<RelatedFile>
}

