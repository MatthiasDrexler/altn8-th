package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

class PostfixSettingsTableModel(postfixSettings: MutableList<PostfixSetting>) :
    PatternTableModel(postfixSettings) {

    override fun convertFromTableData(): List<PostfixSetting> =
        (0 until rowCount).map { row ->
            PostfixSetting(
                getValueAt(row, PATTERN_COLUMN).toString(),
                getValueAt(row, DESCRIPTION_COLUMN).toString(),
                getValueAt(row, CATEGORY_COLUMN).toString()
            )
        }
}
