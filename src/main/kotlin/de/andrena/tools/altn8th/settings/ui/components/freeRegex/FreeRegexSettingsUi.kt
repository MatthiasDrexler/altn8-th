package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextArea
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineLabel
import java.awt.BorderLayout
import javax.swing.JPanel

private val TITLE = I18n.lazyMessage("altn8.freeRegex")
private val DESCRIPTION = I18n.lazyMessage("altn8.freeRegex.description")

private const val SPACING = 8

internal class FreeRegexSettingsUi(private val settingsState: SettingsState) : Ui {
    private val freeRegexSettingsTableModel = FreeRegexSettingsTableModel(settingsState.freeRegexes)
    private val freeRegexSettingsTable = FreeRegexSettingsTable(freeRegexSettingsTableModel).createPanel()

    override val panel: JPanel =
        JPanel(BorderLayout(SPACING, SPACING)).apply {
            this.add(JBTextArea(DESCRIPTION.get()).multilineLabel(), BorderLayout.NORTH)
            this.add(freeRegexSettingsTable, BorderLayout.CENTER)
        }

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
