package de.andrena.tools.altn8th.settings.ui.components.patterns

import de.andrena.tools.altn8th.domain.settings.types.PatternSetting
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

internal abstract class PatternTableModel(private val patternSettings: MutableList<out PatternSetting>): DefaultTableModel() {
    companion object {
        private const val PATTERN = "Pattern"
        private const val DESCRIPTION = "Description"
        private const val CATEGORY = "Category"

        private val columns = arrayOf(PATTERN, DESCRIPTION, CATEGORY)
    }

    init {
        setDataVector(convertToTableData(), columns)
        addTableModelListener(ResetBlankCellTableModelListener(this))
    }

    private fun convertToTableData(): Array<Array<String>> {
        val rows = mutableListOf<Array<String>>()
        patternSettings.forEach { row -> rows.add(arrayOf(row.pattern, row.description, row.category)) }
        return rows.toTypedArray()
    }

    abstract fun convertFromTableData(): List<PatternSetting>

    fun reset() {
        setDataVector(convertToTableData(), columns)
    }
}
