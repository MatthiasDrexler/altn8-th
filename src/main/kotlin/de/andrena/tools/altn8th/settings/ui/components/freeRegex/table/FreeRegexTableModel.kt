package de.andrena.tools.altn8th.settings.ui.components.freeRegex.table

import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import javax.swing.table.DefaultTableModel

internal class FreeRegexTableModel(private val freeRegexes: MutableList<FreeRegexSetting>): DefaultTableModel() {
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

    private fun convertToTableData(): Array<Array<String>> {
        val rows = mutableListOf<Array<String>>()
        freeRegexes.forEach { row -> rows.add(arrayOf(row.origin, row.related, row.category)) }
        return rows.toTypedArray()
    }

    fun convertFromTableData(): List<FreeRegexSetting> =
        (0 until rowCount).map { row ->
            FreeRegexSetting(
                getValueAt(row, 0).toString(),
                getValueAt(row, 1).toString(),
                getValueAt(row, 2).toString()
            )
        }
}
