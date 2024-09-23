package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting

internal class FindRelatedFilesByFreeRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, file: File, settings: SettingsState): Relation? =
        isRelatedBy(settings, origin, file)
            ?.let { Relation(file, origin, FreeRegexRelationType(it)) }

    private fun isRelatedBy(
        settings: SettingsState,
        origin: File,
        file: File
    ): FreeRegexSetting? = settings.freeRegexes
        .filter { origin.nameWithFileExtension().matches(Regex(it.origin, RegexOption.IGNORE_CASE)) }
        .firstOrNull { file.nameWithFileExtension().matches(Regex(it.related, RegexOption.IGNORE_CASE)) }
}
