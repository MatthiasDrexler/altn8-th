package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState

internal class FindRelatedFilesByFreeRelationsStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, allFiles: Collection<File>, settings: SettingsState): RelationsByStrategy {
        val freeRelationsMatchingOrigin = settings.freeRelations
            .filter { origin.nameWithFileExtension().matches(Regex(it.origin)) }
        val relatedFiles = freeRelationsMatchingOrigin
            .flatMap { freeRelation ->
                allFiles
                    .filter { it.nameWithFileExtension().matches(Regex(freeRelation.related)) }
                    .map { Relation(origin, it, FreeRelationRelationType(freeRelation)) }
            }
        return RelationsByStrategy(this, relatedFiles)
    }
}
