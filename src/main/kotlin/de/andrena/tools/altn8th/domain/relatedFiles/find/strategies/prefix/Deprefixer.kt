package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

internal class Deprefixer(private val file: File) {
    fun regardingTo(postfixes: Collection<PrefixSetting>): Map<String, PrefixSetting?> {
        val baseNamesToCorrespondingPrefixSettings = postfixes.associateBy(correspondingBaseName())
        return baseNamesToCorrespondingPrefixSettings + mapOf(file.nameWithoutFileExtension() to null)
    }

    private fun correspondingBaseName() = { prefixSetting: PrefixSetting ->
        val prefixRegex = Regex("^${prefixSetting.pattern}")
        prefixRegex.replace(file.nameWithoutFileExtension(), "")
    }
}
