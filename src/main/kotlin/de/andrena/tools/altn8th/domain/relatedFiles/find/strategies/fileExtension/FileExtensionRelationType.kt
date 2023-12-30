package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType


internal class FileExtensionRelationType : RelationType {
    companion object {
        private const val NAME = "File Extension"
        private const val EXPLANATION = "The opened file is related to this file because they have the same name"
    }

    override fun name() = NAME

    override fun explanation(): String = EXPLANATION
}
