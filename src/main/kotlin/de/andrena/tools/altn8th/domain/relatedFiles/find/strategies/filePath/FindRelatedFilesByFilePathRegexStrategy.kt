package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filePath

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilePathRegexSetting

class FindRelatedFilesByFilePathRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, file: File, settings: SettingsState): Relation? =
        isRelatedBy(settings, origin, file)
            ?.let { FilePathRegexRelation.from(file, origin, it) }

    private fun isRelatedBy(
        settings: SettingsState,
        origin: File,
        file: File
    ): FilePathRegexSetting? {
        val regexOptions = if (settings.caseInsensitiveMatching) setOf(RegexOption.IGNORE_CASE) else emptySet()
        return settings.filePathRegexes
            .filter { origin.path().matches(Regex(it.origin, regexOptions)) }
            .firstOrNull { file.path().matches(Regex(it.related, regexOptions)) }
    }
}
