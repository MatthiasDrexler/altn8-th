package de.andrena.tools.altn8th.settings

import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting


fun buildCleanSettingsState(): SettingsState {
    val settingsState = SettingsState()
    settingsState.prefixes.clear()
    settingsState.postfixes.clear()
    settingsState.filenameRegexes.clear()
    return settingsState
}

fun SettingsState.providePrefixSettingsWithCategories(vararg categories: String) {
    categories.forEach { category ->
        prefixes.add(PrefixSetting("prefix", "prefix", category))
    }
}

fun SettingsState.providePostfixSettingsWithCategories(vararg categories: String) {
    categories.forEach { category ->
        postfixes.add(PostfixSetting("postfix", "postfix", category))
    }
}

fun SettingsState.provideFilenameRegexSettingsWithCategories(vararg categories: String) {
    categories.forEach { category ->
        filenameRegexes.add(FilenameRegexSetting("regex", "regex", category))
    }
}
