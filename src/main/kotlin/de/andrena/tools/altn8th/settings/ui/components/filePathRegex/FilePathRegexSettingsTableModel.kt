package de.andrena.tools.altn8th.settings.ui.components.filePathRegex

import de.andrena.tools.altn8th.domain.settings.types.FilePathRegexSetting
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.ResetBlankCellTableModelListener
import javax.swing.table.DefaultTableModel

class FilePathRegexSettingsTableModel(private val filePathRegexSettings: MutableList<FilePathRegexSetting>) :
    DefaultTableModel() {
    companion object {
        private val ORIGIN = I18n.lazyMessage("altn8.filePathRegex.add.dialog.origin")
        private val RELATED = I18n.lazyMessage("altn8.filePathRegex.add.dialog.related")
        private val CATEGORY = I18n.lazyMessage("altn8.filePathRegex.add.dialog.category")

        private val COLUMNS = arrayOf(ORIGIN.get(), RELATED.get(), CATEGORY.get())
    }

    init {
        setDataVector(convertToTableData(), COLUMNS)
        addTableModelListener(ResetBlankCellTableModelListener(this))
    }

    private fun convertToTableData(): Array<Array<String>> =
        filePathRegexSettings.map { row -> arrayOf(row.origin, row.related, row.category) }
            .toTypedArray()

    fun convertFromTableData(): List<FilePathRegexSetting> =
        (0 until rowCount).map { row ->
            FilePathRegexSetting(
                getValueAt(row, 0).toString(),
                getValueAt(row, 1).toString(),
                getValueAt(row, 2).toString()
            )
        }

    fun reset() {
        setDataVector(convertToTableData(), COLUMNS)
    }
}
