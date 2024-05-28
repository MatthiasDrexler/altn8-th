package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class RelatedFilesWithin(
    private val origin: File,
    private val allFiles: Collection<File>,
    private val settings: SettingsState,
    private val relatedFilesStrategies: Collection<FindRelatedFilesStrategy>
) {
    fun find(): Collection<Relation> = relatedFilesStrategies.flatMap { it.find(origin, allFiles, settings) }
}
