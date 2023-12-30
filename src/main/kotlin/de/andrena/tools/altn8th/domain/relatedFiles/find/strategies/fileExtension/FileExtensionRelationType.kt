package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType

internal class FileExtensionRelationType : RelationType {
    override fun name() = "File Extension"

    override fun explanation(): String = "The opened file is related to this file because they have the same name"
}
