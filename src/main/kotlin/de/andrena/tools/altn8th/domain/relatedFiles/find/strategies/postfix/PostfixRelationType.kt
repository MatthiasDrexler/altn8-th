package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting

internal class PostfixRelationType(
    private val originHop: PostfixSetting?,
    private val relatedFileHop: PostfixSetting
) : RelationType {
    override fun name() = "File Extension"

    override fun explanation(): String =
        if (originHop == null)
            explanationOfDirectHop()
        else explanationOfTransitiveHop()

    private fun explanationOfDirectHop(): String =
        "The opened file is directly related to this file because of the postfix pattern ${relatedFileHop.pattern}"

    private fun explanationOfTransitiveHop(): String =
        "The opened file is transitively related to this file because of the postfix patterns ${originHop?.pattern} and ${relatedFileHop.pattern}"
}
