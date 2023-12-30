package de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class AllRelatedStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, allFiles: Collection<File>, settings: SettingsState): RelationsByStrategy =
        RelationsByStrategy(
            this,
            allFiles.filter { it != origin }.map { Relation(origin, it, AllAreRelatedType()) })
}
