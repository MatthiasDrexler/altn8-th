package de.andrena.tools.altn8th.settings.ui.components.filenameRegex

import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

class FilenameRegexSettingsTableModel(private val filenameRegexSettings: MutableList<FilenameRegexSetting>) :
    DefaultTableModel() {
    companion object {
        private val ORIGIN = I18n.lazyMessage("altn8.filenameRegex.add.dialog.origin")
        private val RELATED = I18n.lazyMessage("altn8.filenameRegex.add.dialog.related")
        private val CATEGORY = I18n.lazyMessage("altn8.filenameRegex.add.dialog.category")

        private val COLUMNS = arrayOf(ORIGIN.get(), RELATED.get(), CATEGORY.get())
    }

    init {
        setDataVector(convertToTableData(), COLUMNS)
        addTableModelListener(ResetBlankCellTableModelListener(this))
    }

    private fun convertToTableData(): Array<Array<String>> =
        filenameRegexSettings.map { row -> arrayOf(row.origin, row.related, row.category) }
            .toTypedArray()

    fun convertFromTableData(): List<FilenameRegexSetting> =
        (0 until rowCount).map { row ->
            FilenameRegexSetting(
                getValueAt(row, 0).toString(),
                getValueAt(row, 1).toString(),
                getValueAt(row, 2).toString()
            )
        }

    fun reset() {
        setDataVector(convertToTableData(), COLUMNS)
    }
}
