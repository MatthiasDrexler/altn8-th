package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType


internal class FileExtensionRelationType : RelationType {
    companion object {
        private const val NAME = "File Extension"
        private const val EXPLANATION = "The opened file is related to this file because they have the same name"
        private const val CATEGORY = "Same Filename"
    }

    override fun name() = NAME
    override fun explanation() = EXPLANATION
    override fun category() = CATEGORY

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
