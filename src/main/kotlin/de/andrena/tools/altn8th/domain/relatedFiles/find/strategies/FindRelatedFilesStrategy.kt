package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal interface FindRelatedFilesStrategy {
    fun find(
        origin: File,
        allFiles: Collection<File>,
        settings: SettingsState
    ): RelationsByType
}

