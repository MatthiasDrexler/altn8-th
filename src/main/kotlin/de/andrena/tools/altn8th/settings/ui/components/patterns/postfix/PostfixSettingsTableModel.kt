package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

internal class PostfixSettingsTableModel(postfixSettings: MutableList<PostfixSetting>) :
    PatternTableModel(postfixSettings) {

    override fun convertFromTableData(): List<PostfixSetting> =
        (0 until rowCount).map { row ->
            PostfixSetting(
                getValueAt(row, 0).toString(),
                getValueAt(row, 1).toString(),
                getValueAt(row, 2).toString()
            )
        }
}
