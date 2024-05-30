package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import de.andrena.tools.altn8th.settings.ui.components.SettingsTable
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

internal class PostfixSettingsTable(freeRegexTableModel: PatternTableModel): SettingsTable(freeRegexTableModel) {
    companion object {
        private const val NO_POSTFIX_PATTERNS_PLACEHOLDER = "No postfix patterns configured yet"
        private const val PATTERN_EXAMPLE = "Postfix Pattern"
        private const val DESCRIPTION_EXAMPLE = "Postfix to relate to related files"
        private const val CATEGORY_EXAMPLE = "Custom"
        private val exampleRow = arrayOf(PATTERN_EXAMPLE, DESCRIPTION_EXAMPLE, CATEGORY_EXAMPLE)
    }

    override fun emptyTablePlaceholderText(): String = NO_POSTFIX_PATTERNS_PLACEHOLDER

    override fun exampleRow(): Array<String> = exampleRow
    override fun addRow(): Array<String>? {
        val dialog = PostfixSettingDialog(PATTERN_EXAMPLE, DESCRIPTION_EXAMPLE, CATEGORY_EXAMPLE);
        if (dialog.showAndGet()){
            return arrayOf(
                dialog.pattern(),
                dialog.description(),
                dialog.category()
            )
        }

        return null
    }
}
