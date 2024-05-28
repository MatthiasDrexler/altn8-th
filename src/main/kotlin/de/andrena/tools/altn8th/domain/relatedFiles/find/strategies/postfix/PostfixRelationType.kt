package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting


internal class PostfixRelationType(
    private val originHop: PostfixSetting?,
    private val relatedFileHop: PostfixSetting
) : RelationType {
    companion object {
        private const val NAME = "Postfix Match"
        private const val DIRECT_HOP =
            "The opened file is directly related to this file because of the postfix pattern"
        private const val TRANSITIVE_HOP =
            "The opened file is transitively related to this file because of the postfix patterns"
        private const val AND = "and"
    }

    override fun name() = NAME

    override fun explanation() =
        if (originHop == null) {
            explanationOfDirectHop()
        } else explanationOfTransitiveHop()

    override fun category() = relatedFileHop.category

    private fun explanationOfDirectHop() =
        "$DIRECT_HOP ${relatedFileHop.pattern}"

    private fun explanationOfTransitiveHop() =
        "$TRANSITIVE_HOP ${originHop?.pattern} $AND ${relatedFileHop.pattern}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostfixRelationType

        if (originHop != other.originHop) return false
        if (relatedFileHop != other.relatedFileHop) return false

        return true
    }

    override fun hashCode(): Int {
        var result = originHop?.hashCode() ?: 0
        result = 31 * result + relatedFileHop.hashCode()
        return result
    }
}
