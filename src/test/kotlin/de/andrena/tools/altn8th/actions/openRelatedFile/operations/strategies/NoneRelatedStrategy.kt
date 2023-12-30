package de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class NoneRelatedStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, allFiles: Collection<File>, settings: SettingsState): RelationsByStrategy =
        RelationsByStrategy(NoneAreRelatedType::class.simpleName!!, listOf())
}
