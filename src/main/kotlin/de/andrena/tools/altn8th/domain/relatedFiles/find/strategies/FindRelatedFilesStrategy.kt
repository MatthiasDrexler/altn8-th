package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.SettingsState
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType

internal interface FindRelatedFilesStrategy {
    fun findRelatedFiles(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByType
}

