package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType

internal class PostfixRelationType : RelationType {
    override fun key() = "FILE_EXTENSION"
}