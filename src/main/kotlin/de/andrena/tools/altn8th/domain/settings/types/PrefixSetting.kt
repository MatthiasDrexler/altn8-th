package de.andrena.tools.altn8th.domain.settings.types

import kotlinx.serialization.Serializable

@Serializable
data class PrefixSetting(
    override var pattern: String,
    override var description: String
) : PatternSetting {
    @Suppress("unused") // needed for deserialization
    constructor() : this("pattern", "description")

    override fun toString(): String = "$pattern ($description)"
}