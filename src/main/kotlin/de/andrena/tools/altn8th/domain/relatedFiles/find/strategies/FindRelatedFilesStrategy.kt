package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.SettingsState

interface FindRelatedFilesStrategy {
    fun find(
        origin: File,
        file: File,
        settings: SettingsState
    ): Relation?
}
