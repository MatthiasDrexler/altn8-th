package de.andrena.tools.altn8th.internationalization

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

@NonNls
private const val BUNDLE = "internationalization.altn8thBundle"

internal object I18n {
    private val INSTANCE = DynamicBundle(I18n::class.java, BUNDLE)

    fun message(
        key: @PropertyKey(resourceBundle = BUNDLE) String,
        vararg params: Any
    ): @Nls String {
        return INSTANCE.getMessage(key, *params)
    }

    fun lazyMessage(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): Supplier<@Nls String> {
        return INSTANCE.getLazyMessage(key, *params)
    }
}
