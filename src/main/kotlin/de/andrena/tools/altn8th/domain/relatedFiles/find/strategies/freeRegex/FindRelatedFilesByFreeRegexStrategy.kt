package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting

internal class FindRelatedFilesByFreeRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, allFiles: Collection<File>, settings: SettingsState): RelationsByStrategy {
        val freeRegexMatchingOrigin = settings.freeRegexes
            .filter { origin.nameWithFileExtension().matches(Regex(it.origin)) }
        val relatedFiles = freeRegexMatchingOrigin.flatMap {
            findRelationsMatching(it, allFiles, origin)
        }
        return RelationsByStrategy(this, relatedFiles)
    }

    private fun findRelationsMatching(
        freeRegex: FreeRegexSetting, allFiles: Collection<File>, origin: File
    ): List<Relation> = allFiles
        .filter { it.nameWithFileExtension().matches(Regex(freeRegex.related)) }
        .map { Relation(origin, it, FreeRegexRelationType(freeRegex)) }
}
