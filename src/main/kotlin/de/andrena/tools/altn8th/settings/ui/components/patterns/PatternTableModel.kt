package de.andrena.tools.altn8th.settings.ui.components.patterns

import de.andrena.tools.altn8th.domain.settings.types.PatternSetting
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

internal abstract class PatternTableModel(private val patternSettings: MutableList<out PatternSetting>): DefaultTableModel() {
    companion object {
        private const val PATTERN = "Pattern"
        @JvmStatic protected val PATTERN_COLUMN = 0
        private const val DESCRIPTION = "Description"
        @JvmStatic protected val DESCRIPTION_COLUMN = 1
        private const val CATEGORY = "Category"
        @JvmStatic protected val CATEGORY_COLUMN = 2

        private val columns = arrayOf(PATTERN, DESCRIPTION, CATEGORY)
    }

    init {
        @Suppress("LeakingThis")
        setDataVector(convertToTableData(), columns)
        @Suppress("LeakingThis")
        addTableModelListener(ResetBlankCellTableModelListener(this))
    }

    private fun convertToTableData(): Array<Array<String>> =
        patternSettings.map { row -> arrayOf(row.pattern, row.description, row.category) }.toTypedArray()

    abstract fun convertFromTableData(): List<PatternSetting>

    fun reset() {
        setDataVector(convertToTableData(), columns)
    }
}
