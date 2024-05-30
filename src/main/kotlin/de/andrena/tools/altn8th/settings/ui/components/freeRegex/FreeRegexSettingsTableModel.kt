package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

internal class FreeRegexSettingsTableModel(private val freeRegexSettings: MutableList<FreeRegexSetting>): DefaultTableModel() {
    companion object {
        private const val ORIGIN = "Origin"
        private const val RELATED = "Related"
        private const val CATEGORY = "Category"

        private val columns = arrayOf(ORIGIN, RELATED, CATEGORY)
    }

    init {
        setDataVector(convertToTableData(), columns)
        addTableModelListener(ResetBlankCellTableModelListener(this))
    }

    private fun convertToTableData(): Array<Array<String>> =
        freeRegexSettings.map { row -> arrayOf(row.origin, row.related, row.category) }
            .toTypedArray()

    fun convertFromTableData(): List<FreeRegexSetting> =
        (0 until rowCount).map { row ->
            FreeRegexSetting(
                getValueAt(row, 0).toString(),
                getValueAt(row, 1).toString(),
                getValueAt(row, 2).toString()
            )
        }

    fun reset() {
        setDataVector(convertToTableData(), columns)
    }
}
