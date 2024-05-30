package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextArea
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineLabel
import java.awt.BorderLayout
import javax.swing.JPanel

private val TITLE = I18n.lazyMessage("altn8.pattern.prefixes")
private val DESCRIPTION = I18n.lazyMessage("altn8.pattern.prefix.description")

private const val SPACING = 8

internal class PrefixSettingsUi(private val settingsState: SettingsState) : Ui {
    private val prefixSettingsTableModel = PrefixSettingsTableModel(settingsState.prefixes)
    private val prefixSettingsTable = PrefixSettingsTable(prefixSettingsTableModel).createPanel()

    override val panel: JPanel =
        JPanel(BorderLayout(SPACING, SPACING)).apply {
            this.add(JBTextArea(DESCRIPTION.get()).multilineLabel(), BorderLayout.NORTH)
            this.add(prefixSettingsTable, BorderLayout.CENTER)
        }

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean = prefixSettingsTableModel.convertFromTableData() != settingsState.prefixes

    override fun apply() {
        settingsState.prefixes = prefixSettingsTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() = prefixSettingsTableModel.reset()
}
