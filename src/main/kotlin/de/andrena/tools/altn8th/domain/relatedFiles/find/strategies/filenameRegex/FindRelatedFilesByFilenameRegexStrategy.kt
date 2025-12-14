package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting

class FindRelatedFilesByFilenameRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, file: File, settings: SettingsState): Relation? =
        try {
            isRelatedBy(settings, origin, file)
                ?.let { FilenameRegexRelation.from(file, origin, it) }
        } catch (e: Exception) {
            null
        }

    private fun isRelatedBy(
        settings: SettingsState,
        origin: File,
        file: File
    ): FilenameRegexSetting? {
        val regexOptions = if (settings.caseInsensitiveMatching) setOf(RegexOption.IGNORE_CASE) else emptySet()

        return settings.filenameRegexes.firstNotNullOfOrNull { setting ->
            val originRegex = Regex(setting.origin, regexOptions)
            val originMatch = originRegex.matchEntire(origin.nameWithFileExtension())

            if (originMatch != null) {
                val namedGroups = extractNamedGroups(setting.origin, originMatch)
                val transformedRelatedPattern = replaceNamedGroupReferences(setting.related, namedGroups)
                val relatedRegex = Regex(transformedRelatedPattern, regexOptions)

                if (relatedRegex.matches(file.nameWithFileExtension())) {
                    setting
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    private fun extractNamedGroups(pattern: String, matchResult: MatchResult): Map<String, String> {
        val namedGroupPattern = Regex("""\(\?<([^>]+)>""")
        val groupNames = namedGroupPattern.findAll(pattern)
            .map { it.groupValues[1] }
            .toList()

        return groupNames.mapNotNull { groupName ->
            try {
                matchResult.groups[groupName]?.let { groupName to it.value }
            } catch (e: Exception) {
                null
            }
        }.toMap()
    }

    private fun replaceNamedGroupReferences(pattern: String, groups: Map<String, String>): String {
        val escapedHashPlaceholder = "\u0000ESCAPED_HASH\u0000"
        var result = pattern.replace("""\\#""", escapedHashPlaceholder)

        val referencePattern = Regex("""#\{([^}]+)\}""")
        result = referencePattern.replace(result) { matchResult ->
            val groupName = matchResult.groupValues[1]
            val capturedValue = groups[groupName] ?: ""
            Regex.escape(capturedValue)
        }

        result = result.replace(escapedHashPlaceholder, "#")

        return result
    }
}
