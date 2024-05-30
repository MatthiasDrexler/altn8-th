package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import de.andrena.tools.altn8th.settings.ui.components.SettingsTable

internal class FreeRegexSettingsTable(private val freeRegexSettingsTableModel: FreeRegexSettingsTableModel) :
    SettingsTable(freeRegexSettingsTableModel) {
    companion object {
        private const val NO_FREE_RELATIONS_PLACEHOLDER = "No free regex relations configured yet"
        private const val ORIGIN_EXAMPLE = "Origin(?<name>\\w*)"
        private const val RELATED_EXAMPLE = "Related(?<name>\\w*)"
        private const val CATEGORY_EXAMPLE = "Custom"
        private val exampleRow = arrayOf(ORIGIN_EXAMPLE, RELATED_EXAMPLE, CATEGORY_EXAMPLE)
    }

    override fun emptyTablePlaceholderText(): String = NO_FREE_RELATIONS_PLACEHOLDER

    override fun exampleRow(): Array<String> = exampleRow

    override fun addRow(): Array<String>? {
        val dialog = FreeRegexSettingDialog(ORIGIN_EXAMPLE, RELATED_EXAMPLE, CATEGORY_EXAMPLE)
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
