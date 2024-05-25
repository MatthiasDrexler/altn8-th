package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.components.freeRegex.table.FreeRegexTable
import de.andrena.tools.altn8th.settings.ui.components.freeRegex.table.FreeRegexTableModel
import javax.swing.JPanel

internal class FreeRegexSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Free Regex"
    }

    private val freeRegexTableModel = FreeRegexTableModel(settingsState.freeRegexes)
    private val freeRegexTable = FreeRegexTable(freeRegexTableModel).createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRegexTable, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)
    }

    override fun isModified(): Boolean = freeRegexTableModel.convertFromTableData() != settingsState.freeRegexes

    override fun apply() {
        settingsState.freeRegexes = freeRegexTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() {
        freeRegexTableModel.reset()
    }
}
