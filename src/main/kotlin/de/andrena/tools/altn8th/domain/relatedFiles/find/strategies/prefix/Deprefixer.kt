package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

class Deprefixer(private val string: String) {
    fun regardingTo(postfixes: Collection<PrefixSetting>): Map<String, PrefixSetting?> {
        val baseNamesToCorrespondingPrefixSettings = postfixes.associateBy(correspondingBaseName())
        return baseNamesToCorrespondingPrefixSettings + mapOf(string to null)
    }

    private fun correspondingBaseName() = { prefixSetting: PrefixSetting ->
        val prefixRegex = Regex("^${prefixSetting.pattern}")
        prefixRegex.replace(string, "")
    }
}
