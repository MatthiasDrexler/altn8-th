package de.andrena.tools.altn8th.settings.ui.components

import com.intellij.ui.CollectionListModel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.dialog.PatternSettingDialog
import javax.swing.JPanel


internal class PostfixSettingsUiComponent(private val settingsState: SettingsState) : Ui {
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

    override fun isModified() = postfixListModel.items != settingsState.postfixes

    override fun apply() {
        settingsState.postfixes = postfixListModel.items
    }

    private fun onAdd() {
        val patternSettingDialog = PatternSettingDialog()
        if (patternSettingDialog.showAndGet()) {
            postfixListModel.add(PostfixSetting(patternSettingDialog.pattern(), ""))
            postfixList.updateUI()
        }
    }

    private fun onEdit() {
        val indexOfSelectedItem = postfixList.selectedIndex
        if (indexOfSelectedItem < 0) {
            return
        }

        val patternSettingDialog = PatternSettingDialog(postfixList.selectedValue.pattern)
        if (patternSettingDialog.showAndGet()) {
            postfixListModel.setElementAt(PostfixSetting(patternSettingDialog.pattern(), ""), indexOfSelectedItem)
            postfixList.updateUI()
        }
    }
}
