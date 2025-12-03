package de.andrena.tools.altn8th.settings.ui.components.filenameRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextArea
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineContextHelp
import java.awt.BorderLayout
import javax.swing.JPanel


class FilenameRegexSettingsUi(private val settingsState: SettingsState) : Ui {
    companion object {
        private val TITLE = I18n.lazyMessage("altn8.filenameRegex")
        private val DESCRIPTION = I18n.lazyMessage("altn8.filenameRegex.description")

        private const val SPACING = 8
    }

    private val filenameRegexSettingsTableModel = FilenameRegexSettingsTableModel(settingsState.filenameRegexes)
    private val filenameRegexSettingsTable = FilenameRegexSettingsTable(filenameRegexSettingsTableModel).createPanel()

    override val panel: JPanel =
        JPanel(BorderLayout(SPACING, SPACING)).apply {
            this.add(JBTextArea(DESCRIPTION.get()).multilineContextHelp(), BorderLayout.NORTH)
            this.add(filenameRegexSettingsTable, BorderLayout.CENTER)
        }

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean =
        filenameRegexSettingsTableModel.convertFromTableData() != settingsState.filenameRegexes

    override fun apply() {
        settingsState.filenameRegexes = filenameRegexSettingsTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() {
        filenameRegexSettingsTableModel.reset()
    }
}
