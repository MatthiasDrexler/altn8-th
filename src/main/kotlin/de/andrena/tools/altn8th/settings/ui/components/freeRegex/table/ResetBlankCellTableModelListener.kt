package de.andrena.tools.altn8th.settings.ui.components.freeRegex.table

import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener
import javax.swing.table.AbstractTableModel

internal class ResetBlankCellTableModelListener(private val tableModel: AbstractTableModel) : TableModelListener {
    companion object {
        private const val SOURCE_COLUMN = 0
        private const val DESTINATION_COLUMN = 1

        private const val EMPTY_PATTERN = "Please specify a regex here"
        private const val EMPTY_CATEGORY = "Please specify a category"
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
        val fallbackValue = if (isPatternColumn(columnOfUpdatedCell)) EMPTY_PATTERN else EMPTY_CATEGORY
        tableModel.setValueAt(fallbackValue, rowOfUpdatedCell, columnOfUpdatedCell)
    }

    private fun isPatternColumn(columnOfUpdatedCell: Int) =
        columnOfUpdatedCell == SOURCE_COLUMN || columnOfUpdatedCell == DESTINATION_COLUMN
}
