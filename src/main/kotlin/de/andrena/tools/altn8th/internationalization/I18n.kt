package de.andrena.tools.altn8th.internationalization

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

@NonNls
private const val LOCALIZATION_BUNDLE = "localization.altn8th"

object I18n {
    private val INSTANCE = DynamicBundle(I18n::class.java, LOCALIZATION_BUNDLE)

    @Nls
    fun message(
        @PropertyKey(resourceBundle = LOCALIZATION_BUNDLE) key: String,
        vararg params: Any
    ): String {
        return INSTANCE.getMessage(key, *params)
    }

    @Nls
    fun lazyMessage(
        @PropertyKey(resourceBundle = LOCALIZATION_BUNDLE) key: String,
        vararg params: Any
    ): Supplier<String> {
        return INSTANCE.getLazyMessage(key, *params)
    }
}
