package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import de.andrena.tools.altn8th.settings.ui.components.SettingsTable
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

internal class PrefixSettingsTable(freeRegexTableModel: PatternTableModel): SettingsTable(freeRegexTableModel) {
    companion object {
        private const val NO_PREFIX_PATTERNS_PLACEHOLDER = "No prefix patterns configured yet"
        private const val PATTERN_EXAMPLE = "Prefix Pattern"
        private const val DESCRIPTION_EXAMPLE = "Prefix to relate to related files"
        private const val CATEGORY_EXAMPLE = "Custom"
        private val exampleRow = arrayOf(PATTERN_EXAMPLE, DESCRIPTION_EXAMPLE, CATEGORY_EXAMPLE)
    }

    override fun emptyTablePlaceholderText(): String = NO_PREFIX_PATTERNS_PLACEHOLDER

    override fun exampleRow(): Array<String> = exampleRow

    override fun addRow(): Array<String>? {
        val dialog = PrefixSettingDialog(PATTERN_EXAMPLE, DESCRIPTION_EXAMPLE, CATEGORY_EXAMPLE)
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
