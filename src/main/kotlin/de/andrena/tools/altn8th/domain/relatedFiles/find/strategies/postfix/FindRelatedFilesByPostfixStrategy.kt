package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

class FindRelatedFilesByPostfixStrategy : FindRelatedFilesStrategy {
    override fun find(
        origin: File,
        file: File,
        settings: SettingsState
    ): Relation? {
        val baseNameToPostfixSettings = Depostfixer(origin.nameWithoutFileExtension()).regardingTo(settings.postfixes)
        return baseNameToPostfixSettings.mapNotNull { (basename, originHop) ->
            val relatedFileHop = settings.postfixes.firstOrNull { relatedFileHop ->
                areNotIdentical(origin, file)
                    && areRelated(basename, file.nameWithoutFileExtension(), relatedFileHop, settings)
            }
            relatedFileHop?.let { PostfixRegexRelation.from(file, origin, originHop, it) }
        }.firstOrNull()
    }

    private fun areNotIdentical(origin: File, relatedFile: File): Boolean = origin != relatedFile

    private fun areRelated(
        basename: String,
        relatedFile: String,
        relatedFileHop: PostfixSetting,
        settings: SettingsState
    ) =
        areRelatedByGivenPattern(basename, relatedFile, relatedFileHop.pattern, settings)
            || areRelatedByGivenPattern(relatedFile, basename, relatedFileHop.pattern, settings)

    private fun areRelatedByGivenPattern(
        first: String,
        second: String,
        postfixPattern: String,
        settings: SettingsState
    ): Boolean {
        val regexOptions = if (settings.caseInsensitiveMatching) setOf(RegexOption.IGNORE_CASE) else emptySet()
        return first.matches(Regex("^${Regex.escape(second)}(${postfixPattern})$", regexOptions))
    }
}
