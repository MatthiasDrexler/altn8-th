package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex

import de.andrena.tools.altn8th.domain.relatedFiles.RelationType
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting


internal class FreeRegexRelationType(
    private val matchedBy: FreeRegexSetting
) : RelationType {
    companion object {
        private const val NAME = "Free Relation Match"
        private const val DIRECT_HOP =
            "The open file is directly related to this file because of the free relation"
    }

    override fun name() = NAME

    override fun category() = matchedBy.category

    override fun explanation() = "$DIRECT_HOP ${matchedBy.origin} -> ${matchedBy.related}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FreeRegexRelationType

        return matchedBy == other.matchedBy
    }

    override fun hashCode(): Int {
        return matchedBy.hashCode()
    }
}
