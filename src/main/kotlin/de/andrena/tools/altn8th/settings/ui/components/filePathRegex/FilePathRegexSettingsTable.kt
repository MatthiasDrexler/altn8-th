package de.andrena.tools.altn8th.settings.ui.components.filePathRegex

import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingsTable

class FilePathRegexSettingsTable(filePathRegexSettingsTableModel: FilePathRegexSettingsTableModel) :
    SettingsTable(filePathRegexSettingsTableModel) {
    companion object {
        private val NO_FILE_PATH_RELATIONS_PLACEHOLDER = I18n.lazyMessage("altn8.filePathRegex.empty")
    }

    override fun emptyTablePlaceholderText(): String = NO_FILE_PATH_RELATIONS_PLACEHOLDER.get()

    override fun addRow(): Array<String>? {
        val dialog = FilePathRegexSettingDialog()
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
