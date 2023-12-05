package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.CollectionListModel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import javax.swing.JPanel


internal class PostfixSettingsUi(private val settingsState: SettingsState) : Ui {
    private val postfixListModel = CollectionListModel(settingsState.postfixes)
    private val postfixList = JBList(postfixListModel)
    private val postfixListWithToolbar = ToolbarDecorator.createDecorator(postfixList)
        .setAddAction { onAdd() }
        .setEditAction { onEdit() }
        .createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(postfixListWithToolbar, 0)
        .panel

    init {
        postfixList.emptyText.setText("No postfixes configured yet")
    }

    override fun isModified(): Boolean = postfixListModel.items == settingsState.postfixes

    override fun apply() {
        settingsState.postfixes = postfixListModel.items
    }

    private fun onAdd() {
        postfixListModel.add("pattern")
        postfixList.updateUI()
    }

    private fun onEdit() {
    }
}
