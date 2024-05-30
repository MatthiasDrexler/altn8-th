package de.andrena.tools.altn8th.settings.ui.components.patterns

import de.andrena.tools.altn8th.domain.settings.types.PatternSetting
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

private val PATTERN = I18n.lazyMessage("altn8.pattern.pattern")
private val DESCRIPTION = I18n.lazyMessage("altn8.pattern.description")
private val CATEGORY = I18n.lazyMessage("altn8.pattern.category")

internal abstract class PatternTableModel(private val patternSettings: MutableList<out PatternSetting>) :
    DefaultTableModel() {

    companion object {
        @JvmStatic
        protected val PATTERN_COLUMN = 0

        @JvmStatic
        protected val DESCRIPTION_COLUMN = 1

        @JvmStatic
        protected val CATEGORY_COLUMN = 2

        private val columns = arrayOf(PATTERN.get(), DESCRIPTION.get(), CATEGORY.get())
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
