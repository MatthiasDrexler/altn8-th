package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

internal class BaseName(private val file: File) {
    fun regardingTo(postfixes: Collection<PostfixSetting>): Map<String, PostfixSetting> =
        postfixes.associateBy(correspondingBaseName())

    private fun correspondingBaseName() = { postfixSetting: PostfixSetting ->
        val postfixRegex = Regex("${postfixSetting.pattern}$")
        postfixRegex.replace(file.nameWithoutFileExtension(), "")
    }
}
