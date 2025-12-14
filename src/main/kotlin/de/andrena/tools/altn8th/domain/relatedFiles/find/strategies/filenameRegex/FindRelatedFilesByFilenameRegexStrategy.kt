package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting

class FindRelatedFilesByFilenameRegexStrategy : FindRelatedFilesStrategy {
    override fun find(origin: File, file: File, settings: SettingsState): Relation? =
        isRelatedBy(settings, origin, file)
            ?.let { FilenameRegexRelation.from(file, origin, it) }

    private fun isRelatedBy(
        settings: SettingsState,
        origin: File,
        file: File
    ): FilenameRegexSetting? {
        val regexOptions = if (settings.caseInsensitiveMatching) setOf(RegexOption.IGNORE_CASE) else emptySet()
        
        return settings.filenameRegexes.firstNotNullOfOrNull { setting ->
            try {
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
            } catch (e: Exception) {
                // If pattern is invalid (e.g., invalid group names with underscores or dollar signs),
                // treat as no match (graceful handling for plugin)
                null
            }
        }
    }

    private fun extractNamedGroups(pattern: String, matchResult: MatchResult): Map<String, String> {
        // Extract all named group names from the pattern using regex
        val namedGroupPattern = Regex("""\(\?<([^>]+)>""")
        val groupNames = namedGroupPattern.findAll(pattern)
            .map { it.groupValues[1] }
            .toList()
        
        // Build a map of group name to captured value (catch exceptions for invalid group names)
        return groupNames.mapNotNull { groupName ->
            try {
                matchResult.groups[groupName]?.let { groupName to it.value }
            } catch (e: Exception) {
                null
            }
        }.toMap()
    }

    private fun replaceNamedGroupReferences(pattern: String, groups: Map<String, String>): String {
        // First, temporarily replace escaped hash signs with a placeholder
        val escapedHashPlaceholder = "\u0000ESCAPED_HASH\u0000"
        var result = pattern.replace("""\\#""", escapedHashPlaceholder)
        
        // Now replace #{groupName} references
        val referencePattern = Regex("""#\{([^}]+)\}""")
        result = referencePattern.replace(result) { matchResult ->
            val groupName = matchResult.groupValues[1]
            // If the named group doesn't exist, substitute with empty string (graceful handling for plugin)
            val capturedValue = groups[groupName] ?: ""
            // Use Kotlin's Regex.escape to escape special regex metacharacters in the captured value
            // This ensures the captured value is treated as a literal string in the pattern
            Regex.escape(capturedValue)
        }
        
        // Restore escaped hash signs as literal # (removing the backslash)
        result = result.replace(escapedHashPlaceholder, "#")
        
        return result
    }
}
