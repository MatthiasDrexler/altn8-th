package de.andrena.tools.altn8th.domain.settings.types

import kotlinx.serialization.Serializable

@Serializable
data class PostfixSetting(
    override var pattern: String,
    override var description: String
) : PatternSetting {
    @Suppress("unused") // needed for deserialization
    constructor() : this("pattern", "description")

    override fun toString(): String = "$pattern ($description)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostfixSetting

        if (pattern != other.pattern) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pattern.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}