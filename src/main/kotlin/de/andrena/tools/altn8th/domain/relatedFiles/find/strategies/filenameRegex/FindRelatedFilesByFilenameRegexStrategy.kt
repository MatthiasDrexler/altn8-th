package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting

internal class FindRelatedFilesByFilenameRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, file: File, settings: SettingsState): Relation? =
        isRelatedBy(settings, origin, file)
            ?.let { FilenameRegexRelation.from(file, origin, it) }

    private fun isRelatedBy(
        settings: SettingsState,
        origin: File,
        file: File
    ): FilenameRegexSetting? {
        val regexOptions = if (settings.caseInsensitiveMatching) setOf(RegexOption.IGNORE_CASE) else emptySet()
        return settings.filenameRegexes
            .filter { origin.nameWithFileExtension().matches(Regex(it.origin, regexOptions)) }
            .firstOrNull { file.nameWithFileExtension().matches(Regex(it.related, regexOptions)) }
    }
}
