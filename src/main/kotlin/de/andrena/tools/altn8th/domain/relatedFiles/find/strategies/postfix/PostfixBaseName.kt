package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

internal class PostfixBaseName(private val file: File) {
    fun regardingTo(postfixes: Collection<PostfixSetting>) =
        postfixes
            .map { postfixPattern -> Regex("${postfixPattern.pattern}$") }
            .map { postfixRegex -> postfixRegex.replace(file.nameWithoutFileExtension(), "") }
            .plus(file.nameWithoutFileExtension())
            .distinct()
}
