package de.andrena.tools.altn8th.domain.settings.types

import com.intellij.util.xmlb.annotations.Tag
import kotlinx.serialization.Serializable

@Serializable
@Tag("FreeRegexSetting")
data class FilenameRegexSetting(var origin: String, var related: String, var category: String) {
    @Suppress("unused") // needed for deserialization
    constructor() : this("origin", "related", "category")

    override fun toString(): String {
        return "$origin -> $related ($category)"
    }
}
