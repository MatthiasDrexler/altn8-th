package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineLabel
import javax.swing.JPanel

private val TITLE = I18n.lazyMessage("altn8.pattern.postfixes")
private val DESCRIPTION = I18n.lazyMessage("altn8.pattern.postfix.description")

private const val VERTICAL_SPACING = 8

internal class PostfixSettingsUi(private val settingsState: SettingsState) : Ui {
    private val postfixSettingsTableModel = PostfixSettingsTableModel(settingsState.postfixes)
    private val postfixSettingsTable = PostfixSettingsTable(postfixSettingsTableModel).createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponent(JBTextArea(DESCRIPTION.get()).multilineLabel())
        .addVerticalGap(VERTICAL_SPACING)
        .addComponentFillVertically(postfixSettingsTable, 0)
        .panel

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean = postfixSettingsTableModel.convertFromTableData() != settingsState.postfixes

    override fun apply() {
        settingsState.postfixes = postfixSettingsTableModel.convertFromTableData().toMutableList()
    }

    override fun reset() = postfixSettingsTableModel.reset()
}
