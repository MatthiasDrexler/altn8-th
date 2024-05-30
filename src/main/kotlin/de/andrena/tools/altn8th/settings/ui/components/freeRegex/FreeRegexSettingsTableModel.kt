package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

private val ORIGIN = I18n.lazyMessage("altn8.freeRegex.add.dialog.origin")
private val RELATED = I18n.lazyMessage("altn8.freeRegex.add.dialog.related")
private val CATEGORY = I18n.lazyMessage("altn8.freeRegex.add.dialog.category")

internal class FreeRegexSettingsTableModel(private val freeRegexSettings: MutableList<FreeRegexSetting>) :
    DefaultTableModel() {
    companion object {
        private val COLUMNS = arrayOf(ORIGIN.get(), RELATED.get(), CATEGORY.get())
    }

    init {
        setDataVector(convertToTableData(), COLUMNS)
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
        setDataVector(convertToTableData(), COLUMNS)
    }
}
