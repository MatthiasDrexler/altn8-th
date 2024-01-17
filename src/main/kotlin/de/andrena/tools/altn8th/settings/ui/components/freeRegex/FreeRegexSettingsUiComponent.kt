package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FreeRelationSetting
import de.andrena.tools.altn8th.settings.ui.Ui
import javax.swing.JPanel
import javax.swing.table.DefaultTableModel

internal class FreeRegexSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Free Regex"
        private const val NO_FREE_RELATIONS_PLACEHOLDER = "No free regex relations configured yet"
        private const val ORIGIN = "Origin"
        private const val RELATED = "Related"
    }

    private val freeRegexTableModel = DefaultTableModel(convertToTableData(), arrayOf(ORIGIN, RELATED))
    private val freeRegexTable = JBTable(freeRegexTableModel)
    private val freeRegexTableWithToolbar = ToolbarDecorator.createDecorator(freeRegexTable)
        .setAddAction { onAdd() }
        .setRemoveAction { onRemove() }
        .setEditAction { onEdit() }
        .setMoveDownAction { onMoveDown() }
        .setMoveUpAction { onMoveUp() }
        .createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRegexTableWithToolbar, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)

        freeRegexTable.emptyText.setText(NO_FREE_RELATIONS_PLACEHOLDER)
    }

    override fun isModified(): Boolean = convertFromTableData() != settingsState.freeRelations


    override fun apply() {
        settingsState.freeRelations = convertFromTableData().toMutableList()
    }

    private fun onAdd() {
    }

    private fun onRemove() {
    }

    private fun onEdit() {
    }

    private fun onMoveDown() {
    }

    private fun onMoveUp() {
    }

    private fun convertToTableData(): Array<Array<String>> {
        val rows = mutableListOf<Array<String>>()
        settingsState.freeRelations.forEach { row -> rows.add(arrayOf(row.origin, row.related)) }
        return rows.toTypedArray()
    }

    private fun convertFromTableData(): List<FreeRelationSetting> =
        (0 until freeRegexTableModel.rowCount).map { row ->
            FreeRelationSetting(
                freeRegexTableModel.getValueAt(row, 0).toString(),
                freeRegexTableModel.getValueAt(row, 1).toString()
            )
        }
}
