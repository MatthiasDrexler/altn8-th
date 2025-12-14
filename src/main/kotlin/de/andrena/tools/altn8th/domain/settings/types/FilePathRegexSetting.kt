package de.andrena.tools.altn8th.domain.settings.types

import kotlinx.serialization.Serializable

@Serializable
data class FilePathRegexSetting(var origin: String, var related: String, var category: String) {
    @Suppress("unused") // needed for deserialization
    constructor() : this("origin", "related", "category")

    override fun toString(): String {
        return "$origin -> $related ($category)"
    }
}
