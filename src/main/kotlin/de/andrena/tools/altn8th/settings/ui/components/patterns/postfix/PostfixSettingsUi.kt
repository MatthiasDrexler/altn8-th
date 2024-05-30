package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import com.intellij.ui.IdeBorderFactory
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.Ui
import javax.swing.JPanel


internal class PostfixSettingsUi(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Postfix Patterns"
    }

    private val postfixSettingsTableModel = PostfixSettingsTableModel(settingsState.postfixes)
    private val postfixSettingsTable = PostfixSettingsTable(postfixSettingsTableModel).createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(postfixSettingsTable, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)
    }

    override fun isModified(): Boolean = postfixSettingsTableModel.convertFromTableData() != settingsState.postfixes

    override fun apply() {
        settingsState.postfixes = postfixSettingsTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() = postfixSettingsTableModel.reset()
}
