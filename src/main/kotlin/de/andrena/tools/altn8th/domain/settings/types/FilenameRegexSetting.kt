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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilenameRegexSetting

        if (origin != other.origin) return false
        if (related != other.related) return false

        return true
    }

    override fun hashCode(): Int {
        var result = origin.hashCode()
        result = 31 * result + related.hashCode()
        return result
    }
}
