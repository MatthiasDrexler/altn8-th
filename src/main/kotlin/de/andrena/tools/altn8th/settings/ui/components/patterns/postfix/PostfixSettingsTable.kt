package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingsTable
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

private val NO_POSTFIX_PATTERNS_PLACEHOLDER = I18n.lazyMessage("altn8.pattern.postfix.empty")

class PostfixSettingsTable(postfixRegexTableModel: PatternTableModel): SettingsTable(postfixRegexTableModel) {
    override fun emptyTablePlaceholderText(): String = NO_POSTFIX_PATTERNS_PLACEHOLDER.get()

    override fun addRow(): Array<String>? {
        val dialog = PostfixSettingDialog()
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
