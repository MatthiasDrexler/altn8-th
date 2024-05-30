package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingsTable

private val NO_FREE_RELATIONS_PLACEHOLDER = I18n.lazyMessage("altn8.freeRegex.empty")

internal class FreeRegexSettingsTable(private val freeRegexSettingsTableModel: FreeRegexSettingsTableModel) :
    SettingsTable(freeRegexSettingsTableModel) {
    override fun emptyTablePlaceholderText(): String = NO_FREE_RELATIONS_PLACEHOLDER.get()

    override fun addRow(): Array<String>? {
        val dialog = FreeRegexSettingDialog()
        if (dialog.showAndGet()) {
            return arrayOf(
                dialog.origin(),
                dialog.related(),
                dialog.category()
            )
        }
        return null
    }
}
