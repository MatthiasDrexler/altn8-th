package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting

internal class FindRelatedFilesByFreeRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, file: File, settings: SettingsState): Relation? {
        val matchingFreeRegexSetting = isRelatedBy(settings, origin, file)
        return if (matchingFreeRegexSetting == null) null else Relation(file, origin, FreeRegexRelationType(matchingFreeRegexSetting))
    }

    private fun isRelatedBy(
        settings: SettingsState,
        origin: File,
        file: File
    ): FreeRegexSetting? = settings.freeRegexes
        .filter { origin.nameWithFileExtension().matches(Regex(it.origin)) }
        .firstOrNull { file.nameWithFileExtension().matches(Regex(it.related)) }

    private fun findRelationsMatching(
        freeRegex: FreeRegexSetting, allFiles: Collection<File>, origin: File
    ): List<Relation> = allFiles
        .filter { it.nameWithFileExtension().matches(Regex(freeRegex.related)) }
        .map { Relation(it, origin, FreeRegexRelationType(freeRegex)) }
}
