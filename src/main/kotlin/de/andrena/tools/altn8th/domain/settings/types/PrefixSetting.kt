package de.andrena.tools.altn8th.domain.settings.types

import kotlinx.serialization.Serializable

@Serializable
data class PrefixSetting(
    override var pattern: String,
    override var description: String,
    override var category: String
) : PatternSetting {
    @Suppress("unused") // needed for deserialization
    constructor() : this("pattern", "description", "category")

    override fun toString(): String = "$pattern ($description) - $category"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrefixSetting

        if (pattern != other.pattern) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pattern.hashCode()
        result = 31 * result + category.hashCode()
        return result
    }
}
