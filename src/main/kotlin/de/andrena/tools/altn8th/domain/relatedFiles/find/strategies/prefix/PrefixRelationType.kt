package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting

internal class PrefixRelationType(
    private val originHop: PrefixSetting?,
    private val relatedFileHop: PrefixSetting
) : RelationType {
    companion object {
        private const val NAME = "Postfix Match"
        private const val DIRECT_HOP =
            "The opened file is directly related to this file because of the prefix pattern"
        private const val TRANSITIVE_HOP =
            "The opened file is transitively related to this file because of the prefix patterns"
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

        other as PrefixRelationType

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
