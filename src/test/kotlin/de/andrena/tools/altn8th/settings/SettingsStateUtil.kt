package de.andrena.tools.altn8th.settings

import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting


internal fun buildCleanSettingsState(): SettingsState {
    val settingsState = SettingsState()
    settingsState.prefixes.clear()
    settingsState.postfixes.clear()
    settingsState.freeRegexes.clear()
    return settingsState
}

internal fun SettingsState.providePrefixSettingsWithCategories(vararg categories: String) {
    categories.forEach { category ->
        prefixes.add(PrefixSetting("prefix", "prefix", category))
    }
}

internal fun SettingsState.providePostfixSettingsWithCategories(vararg categories: String) {
    categories.forEach { category ->
        postfixes.add(PostfixSetting("postfix", "postfix", category))
    }
}

internal fun SettingsState.provideFreeRegexSettingsWithCategories(vararg categories: String) {
    categories.forEach { category ->
        freeRegexes.add(FreeRegexSetting("regex", "regex", category))
    }
}
