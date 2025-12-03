package de.andrena.tools.altn8th.domain.settings.types

import kotlinx.serialization.Serializable

@Serializable
data class PostfixSetting(
    override var pattern: String,
    override var description: String,
    override var category: String
) : PatternSetting {
    @Suppress("unused") // needed for deserialization
    constructor() : this("pattern", "description", "category")

    override fun toString(): String = "$pattern ($description) - $category"
}
