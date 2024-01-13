package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

internal class BaseName(private val file: File) {
    fun regardingTo(postfixes: Collection<PrefixSetting>): Map<String, PrefixSetting?> {
        val baseNamesToCorrespondingPrefixSettings: MutableMap<String, PrefixSetting?> =
            postfixes.associateBy(correspondingBaseName()).toMutableMap()
        baseNamesToCorrespondingPrefixSettings[file.nameWithoutFileExtension()] = null
        return baseNamesToCorrespondingPrefixSettings
    }

    private fun correspondingBaseName() = { prefixSetting: PrefixSetting ->
        val prefixRegex = Regex("^${prefixSetting.pattern}")
        prefixRegex.replace(file.nameWithoutFileExtension(), "")
    }
}
