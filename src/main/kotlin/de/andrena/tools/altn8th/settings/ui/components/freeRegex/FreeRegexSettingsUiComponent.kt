package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.components.freeRegex.table.FreeRegexTableModel
import javax.swing.JPanel

internal class FreeRegexSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Free Regex"
        private const val NO_FREE_RELATIONS_PLACEHOLDER = "No free regex relations configured yet"
        private const val ORIGIN_EXAMPLE = "Origin(?<name>\\w*)"
        private const val RELATED_EXAMPLE = "Related(?<name>\\w*)"
        private const val CATEGORY_EXAMPLE = "Custom"
        private val exampleRow = arrayOf(ORIGIN_EXAMPLE, RELATED_EXAMPLE, CATEGORY_EXAMPLE)
    }

    private val freeRegexTableModel = FreeRegexTableModel(settingsState.freeRegexes)
    private val freeRegexTable = JBTable(freeRegexTableModel)
    private val freeRegexTableWithToolbar = ToolbarDecorator.createDecorator(freeRegexTable)
        .setAddAction { onAdd() }
        .setRemoveAction { onRemove() }
        .setMoveDownAction { onMoveDown() }
        .setMoveUpAction { onMoveUp() }
        .createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRegexTableWithToolbar, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)

        freeRegexTable.emptyText.setText(NO_FREE_RELATIONS_PLACEHOLDER)
        freeRegexTable.tableHeader.reorderingAllowed = false
    }

    override fun isModified(): Boolean = freeRegexTableModel.convertFromTableData() != settingsState.freeRegexes

    override fun apply() {
        settingsState.freeRegexes = freeRegexTableModel.convertFromTableData().toMutableList()
    }

    private fun onAdd() {
        this.freeRegexTableModel.addRow(exampleRow)
    }

    private fun onRemove() {
        val selectedRows = this.freeRegexTable.selectedRows.sortedArrayDescending()
        selectedRows.forEach { this.freeRegexTableModel.removeRow(it) }
    }

    private fun onMoveDown() {
        val selectedRows = this.freeRegexTable.selectedRows.sortedArrayDescending()
        if (selectedRows.max() >= this.freeRegexTableModel.rowCount) {
            return
        }

        selectedRows.forEach { moveSingleLineDown(it) }
        this.freeRegexTable.clearSelection()
        selectedRows.map { it + 1 }.forEach { this.freeRegexTable.addRowSelectionInterval(it, it) }
    }

    private fun moveSingleLineDown(index: Int) {
        val targetIndex = index + 1
        if (targetIndex < this.freeRegexTableModel.rowCount) {
            this.freeRegexTableModel.moveRow(index, index, targetIndex)
        }
    }

    private fun onMoveUp() {
        val selectedRows = this.freeRegexTable.selectedRows.sortedArray()
        if (selectedRows.min() < 1) {
            return
        }

        selectedRows.forEach { moveSingleLineUp(it) }
        this.freeRegexTable.clearSelection()
        selectedRows.map { it - 1 }.forEach { this.freeRegexTable.addRowSelectionInterval(it, it) }
    }

    private fun moveSingleLineUp(index: Int) {
        val targetIndex = index - 1
        if (targetIndex >= 0) {
            this.freeRegexTableModel.moveRow(index, index, targetIndex)
        }
    }
}
