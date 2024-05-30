package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternTableModel

internal class PrefixSettingsTableModel(patternSettings: MutableList<PrefixSetting>) :
    PatternTableModel(patternSettings) {

    override fun convertFromTableData(): List<PrefixSetting> =
        (0 until rowCount).map { row ->
            PrefixSetting(
                getValueAt(row, 0).toString(),
                getValueAt(row, 1).toString(),
                getValueAt(row, 2).toString()
            )
        }
}
