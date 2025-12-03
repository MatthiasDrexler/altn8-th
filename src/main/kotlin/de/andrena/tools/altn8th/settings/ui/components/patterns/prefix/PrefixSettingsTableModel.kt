package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

class PrefixSettingsTableModel(prefixSettings: MutableList<PrefixSetting>) :
    PatternTableModel(prefixSettings) {
    override fun convertFromTableData(): List<PrefixSetting> =
        (0 until rowCount).map { row ->
            PrefixSetting(
                getValueAt(row, PATTERN_COLUMN).toString(),
                getValueAt(row, DESCRIPTION_COLUMN).toString(),
                getValueAt(row, CATEGORY_COLUMN).toString()
            )
        }
}
