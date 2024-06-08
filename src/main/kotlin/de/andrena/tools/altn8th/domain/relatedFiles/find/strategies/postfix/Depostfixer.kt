package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

internal class Depostfixer(private val string: String) {
    fun regardingTo(postfixes: Collection<PostfixSetting>): Map<String, PostfixSetting?> {
        val baseNamesToCorrespondingPostfixSettings = postfixes.associateBy(correspondingBaseName())
        return baseNamesToCorrespondingPostfixSettings + mapOf(string to null)
    }

    private fun correspondingBaseName() = { postfixSetting: PostfixSetting ->
        val postfixRegex = Regex("${postfixSetting.pattern}$")
        postfixRegex.replace(string, "")
    }
}
