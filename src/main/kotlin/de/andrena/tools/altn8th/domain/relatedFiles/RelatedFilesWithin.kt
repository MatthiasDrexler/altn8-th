package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

class RelatedFilesWithin(
    private val relatedFilesStrategies: Collection<FindRelatedFilesStrategy>,
    private val settings: SettingsState
) {
    fun findFor(origin: File, allFiles: Collection<File>): Collection<Relation> =
        allFiles.flatMap { findRelations(origin, it) }

    private fun findRelations(origin: File, currentFile: File): Collection<Relation> =
        relatedFilesStrategies.mapNotNull { it.find(origin, currentFile, settings) }
}
