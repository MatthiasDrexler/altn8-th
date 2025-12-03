package de.andrena.tools.altn8th.settings.ui.components

import de.andrena.tools.altn8th.internationalization.I18n
import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener
import javax.swing.table.AbstractTableModel


internal class ResetBlankCellTableModelListener(private val tableModel: AbstractTableModel) : TableModelListener {
    companion object {
        private val EMPTY_PATTERN = I18n.lazyMessage("altn8.ui.validation.pattern.empty")
        private val EMPTY_CATEGORY = I18n.lazyMessage("altn8.ui.validation.category.empty")

        private const val SOURCE_COLUMN = 0
        private const val DESTINATION_COLUMN = 1
    }

    override fun tableChanged(event: TableModelEvent) {
        val rowOfUpdatedCell = event.firstRow
        val columnOfUpdatedCell = event.column

        if (isNoSingleCellUpdate(rowOfUpdatedCell, columnOfUpdatedCell)) {
            return
        }

        if (isBlank(tableModel.getValueAt(rowOfUpdatedCell, columnOfUpdatedCell))) {
            fillFallbackValue(rowOfUpdatedCell, columnOfUpdatedCell)
        }
    }

    private fun isBlank(value: Any?) = (value as? String).orEmpty().isEmpty()

    private fun isNoSingleCellUpdate(rowOfUpdatedCell: Int, columnOfUpdatedCell: Int) =
        rowOfUpdatedCell == TableModelEvent.HEADER_ROW || columnOfUpdatedCell == TableModelEvent.ALL_COLUMNS

    private fun fillFallbackValue(rowOfUpdatedCell: Int, columnOfUpdatedCell: Int) {
        val fallbackValue = if (isPatternColumn(columnOfUpdatedCell)) EMPTY_PATTERN.get() else EMPTY_CATEGORY.get()
        tableModel.setValueAt(fallbackValue, rowOfUpdatedCell, columnOfUpdatedCell)
    }

    private fun isPatternColumn(columnOfUpdatedCell: Int) =
        columnOfUpdatedCell == SOURCE_COLUMN || columnOfUpdatedCell == DESTINATION_COLUMN
}
