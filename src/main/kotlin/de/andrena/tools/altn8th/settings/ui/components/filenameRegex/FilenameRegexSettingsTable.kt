package de.andrena.tools.altn8th.settings.ui.components.filenameRegex

import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingsTable

internal class FilenameRegexSettingsTable(filenameRegexSettingsTableModel: FilenameRegexSettingsTableModel) :
    SettingsTable(filenameRegexSettingsTableModel) {
    companion object {
        private val NO_FILENAME_RELATIONS_PLACEHOLDER = I18n.lazyMessage("altn8.filenameRegex.empty")
    }

    override fun emptyTablePlaceholderText(): String = NO_FILENAME_RELATIONS_PLACEHOLDER.get()

    override fun addRow(): Array<String>? {
        val dialog = FilenameRegexSettingDialog()
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
