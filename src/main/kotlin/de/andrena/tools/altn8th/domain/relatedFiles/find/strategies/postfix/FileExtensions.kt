package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File


fun File.baseNamesFromPostfixes(postfixPatterns: Collection<String>): Collection<String> =
    postfixPatterns
        .map { postfixPattern -> Regex("${postfixPattern}$") }
        .map { postfixRegex -> postfixRegex.replace(nameWithoutFileExtension(), "") }
        .plus(nameWithoutFileExtension())
        .distinct()
