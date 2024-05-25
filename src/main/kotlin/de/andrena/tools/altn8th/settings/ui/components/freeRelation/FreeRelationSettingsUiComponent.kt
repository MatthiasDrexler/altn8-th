package de.andrena.tools.altn8th.settings.ui.components.freeRelation

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRelationSetting
import de.andrena.tools.altn8th.settings.ui.Ui
import javax.swing.JPanel
import javax.swing.table.DefaultTableModel

internal class FreeRelationSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Free Regex"
        private const val NO_FREE_RELATIONS_PLACEHOLDER = "No free regex relations configured yet"
        private const val ORIGIN = "Origin"
        private const val ORIGIN_EXAMPLE = "Origin(?<name>\\w*)"
        private const val RELATED = "Related"
        private const val RELATED_EXAMPLE = "Related(?<name>\\w*)"
        private const val CATEGORY = "Category"
        private const val CATEGORY_EXAMPLE = "Custom"
    }

    private val freeRelationTableModel = DefaultTableModel(convertToTableData(), arrayOf(ORIGIN, RELATED, CATEGORY))
    private val freeRelationTable = JBTable(freeRelationTableModel)
    private val freeRelationTableWithToolbar = ToolbarDecorator.createDecorator(freeRelationTable)
        .setAddAction { onAdd() }
        .setRemoveAction { onRemove() }
        .setMoveDownAction { onMoveDown() }
        .setMoveUpAction { onMoveUp() }
        .createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRelationTableWithToolbar, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)

        freeRelationTable.emptyText.setText(NO_FREE_RELATIONS_PLACEHOLDER)
        freeRelationTable.tableHeader.reorderingAllowed = false
    }

    override fun isModified(): Boolean = convertFromTableData() != settingsState.freeRelations


    override fun apply() {
        settingsState.freeRelations = convertFromTableData().toMutableList()
    }

    private fun onAdd() {
        this.freeRelationTableModel.addRow(arrayOf(ORIGIN_EXAMPLE, RELATED_EXAMPLE, CATEGORY_EXAMPLE))
    }

    private fun onRemove() {
        val selectedRows = this.freeRelationTable.selectedRows.sortedArrayDescending()
        selectedRows.forEach { this.freeRelationTableModel.removeRow(it) }
    }

    private fun onMoveDown() {
        val selectedRows = this.freeRelationTable.selectedRows.sortedArrayDescending()
        if (selectedRows.max() >= this.freeRelationTableModel.rowCount) {
            return
        }

        selectedRows.forEach { moveSingleLineDown(it) }
        this.freeRelationTable.clearSelection()
        selectedRows.map { it + 1 }.forEach { this.freeRelationTable.addRowSelectionInterval(it, it) }
    }

    private fun moveSingleLineDown(index: Int) {
        val targetIndex = index + 1
        if (targetIndex < this.freeRelationTableModel.rowCount) {
            this.freeRelationTableModel.moveRow(index, index, targetIndex)
        }
    }

    private fun onMoveUp() {
        val selectedRows = this.freeRelationTable.selectedRows.sortedArray()
        if (selectedRows.min() < 1) {
            return
        }

        selectedRows.forEach { moveSingleLineUp(it) }
        this.freeRelationTable.clearSelection()
        selectedRows.map { it - 1 }.forEach { this.freeRelationTable.addRowSelectionInterval(it, it) }
    }

    private fun moveSingleLineUp(index: Int) {
        val targetIndex = index - 1
        if (targetIndex >= 0) {
            this.freeRelationTableModel.moveRow(index, index, targetIndex)
        }
    }

    private fun convertToTableData(): Array<Array<String>> {
        val rows = mutableListOf<Array<String>>()
        settingsState.freeRelations.forEach { row -> rows.add(arrayOf(row.origin, row.related, row.category)) }
        return rows.toTypedArray()
    }

    private fun convertFromTableData(): List<FreeRelationSetting> =
        (0 until freeRelationTableModel.rowCount).map { row ->
            FreeRelationSetting(
                freeRelationTableModel.getValueAt(row, 0).toString(),
                freeRelationTableModel.getValueAt(row, 1).toString(),
                freeRelationTableModel.getValueAt(row, 2).toString()
            )
        }
}
