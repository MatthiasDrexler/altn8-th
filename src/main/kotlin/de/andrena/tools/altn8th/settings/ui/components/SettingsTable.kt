package de.andrena.tools.altn8th.settings.ui.components

import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import javax.swing.JPanel
import javax.swing.table.DefaultTableModel

abstract class SettingsTable(private val tableModel: DefaultTableModel) {
    private val table = JBTable(tableModel)

    abstract fun emptyTablePlaceholderText(): String
    abstract fun addRow(): Array<String>?

    fun createPanel(): JPanel {
        table.emptyText.setText(emptyTablePlaceholderText())
        table.tableHeader.reorderingAllowed = false
        return ToolbarDecorator.createDecorator(table)
            .setAddAction { onAdd() }
            .setRemoveAction { onRemove() }
            .setMoveDownAction { onMoveDown() }
            .setMoveUpAction { onMoveUp() }
            .createPanel()
    }

    private fun onAdd() {
        addRow()?.let { this.tableModel.addRow(it) }
    }

    private fun onRemove() {
        val selectedRows = this.table.selectedRows.sortedArrayDescending()
        selectedRows.forEach { this.tableModel.removeRow(it) }
    }

    private fun onMoveDown() {
        val selectedRows = this.table.selectedRows.sortedArrayDescending()
        if (selectedRows.max() >= this.tableModel.rowCount) {
            return
        }

        selectedRows.forEach { moveSingleLineDown(it) }
        this.table.clearSelection()
        selectedRows.map { it + 1 }.forEach { this.table.addRowSelectionInterval(it, it) }
    }

    private fun moveSingleLineDown(index: Int) {
        val targetIndex = index + 1
        if (targetIndex < this.tableModel.rowCount) {
            this.tableModel.moveRow(index, index, targetIndex)
        }
    }

    private fun onMoveUp() {
        val selectedRows = this.table.selectedRows.sortedArray()
        if (selectedRows.min() < 1) {
            return
        }

        selectedRows.forEach { moveSingleLineUp(it) }
        this.table.clearSelection()
        selectedRows.map { it - 1 }.forEach { this.table.addRowSelectionInterval(it, it) }
    }

    private fun moveSingleLineUp(index: Int) {
        val targetIndex = index - 1
        if (targetIndex >= 0) {
            this.tableModel.moveRow(index, index, targetIndex)
        }
    }
}
