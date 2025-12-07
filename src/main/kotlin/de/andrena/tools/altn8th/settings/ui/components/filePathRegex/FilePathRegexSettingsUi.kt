package de.andrena.tools.altn8th.settings.ui.components.filePathRegex

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextArea
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineContextHelp
import java.awt.BorderLayout
import javax.swing.JPanel


class FilePathRegexSettingsUi(private val settingsState: SettingsState) : Ui {
    companion object {
        private val TITLE = I18n.lazyMessage("altn8.filePathRegex")
        private val DESCRIPTION = I18n.lazyMessage("altn8.filePathRegex.description")

        private const val SPACING = 8
    }

    private val filePathRegexSettingsTableModel = FilePathRegexSettingsTableModel(settingsState.filePathRegexes)
    private val filePathRegexSettingsTable = FilePathRegexSettingsTable(filePathRegexSettingsTableModel).createPanel()

    override val panel: JPanel =
        JPanel(BorderLayout(SPACING, SPACING)).apply {
            this.add(JBTextArea(DESCRIPTION.get()).multilineContextHelp(), BorderLayout.NORTH)
            this.add(filePathRegexSettingsTable, BorderLayout.CENTER)
        }

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean =
        filePathRegexSettingsTableModel.convertFromTableData() != settingsState.filePathRegexes

    override fun apply() {
        settingsState.filePathRegexes = filePathRegexSettingsTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() {
        filePathRegexSettingsTableModel.reset()
    }
}
