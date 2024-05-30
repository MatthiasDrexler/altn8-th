package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import javax.swing.JPanel

private val TITLE = I18n.lazyMessage("altn8.freeRegex")

internal class FreeRegexSettingsUi(private val settingsState: SettingsState) : Ui {
    private val freeRegexSettingsTableModel = FreeRegexSettingsTableModel(settingsState.freeRegexes)
    private val freeRegexSettingsTable = FreeRegexSettingsTable(freeRegexSettingsTableModel).createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRegexSettingsTable, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean = freeRegexSettingsTableModel.convertFromTableData() != settingsState.freeRegexes

    override fun apply() {
        settingsState.freeRegexes = freeRegexSettingsTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() {
        freeRegexSettingsTableModel.reset()
    }
}
