package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingsTable
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

private val NO_PREFIX_PATTERNS_PLACEHOLDER = I18n.lazyMessage("altn8.pattern.prefix.empty")

internal class PrefixSettingsTable(prefixRegexTableModel: PatternTableModel): SettingsTable(prefixRegexTableModel) {
    override fun emptyTablePlaceholderText(): String = NO_PREFIX_PATTERNS_PLACEHOLDER.get()

    override fun addRow(): Array<String>? {
        val dialog = PrefixSettingDialog()
        if (dialog.showAndGet()) {
            return arrayOf(
                dialog.pattern(),
                dialog.description(),
                dialog.category()
            )
        }

        return null
    }
}
