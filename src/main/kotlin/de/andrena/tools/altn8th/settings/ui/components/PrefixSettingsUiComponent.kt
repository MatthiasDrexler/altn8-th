package de.andrena.tools.altn8th.settings.ui.components

import com.intellij.ui.CollectionListModel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.dialog.PatternSettingDialog
import javax.swing.JPanel


internal class PrefixSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    private val prefixListModel = CollectionListModel(settingsState.prefixes)
    private val prefixList = JBList(prefixListModel)
    private val prefixListWithToolbar = ToolbarDecorator.createDecorator(prefixList)
        .setAddAction { onAdd() }
        .setEditAction { onEdit() }
        .createPanel()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(prefixListWithToolbar, 0)
        .panel

    init {
        prefixList.emptyText.setText("No prefixes configured yet")
    }

    override fun isModified() = prefixListModel.items != settingsState.prefixes

    override fun apply() {
        settingsState.prefixes = prefixListModel.items
    }

    private fun onAdd() {
        val patternSettingDialog = PatternSettingDialog()
        if (patternSettingDialog.showAndGet()) {
            prefixListModel.add(PrefixSetting(patternSettingDialog.pattern(), ""))
            prefixList.updateUI()
        }
    }

    private fun onEdit() {
        val indexOfSelectedItem = prefixList.selectedIndex
        if (indexOfSelectedItem < 0) {
            return
        }

        val patternSettingDialog = PatternSettingDialog(prefixList.selectedValue.pattern)
        if (patternSettingDialog.showAndGet()) {
            prefixListModel.setElementAt(PrefixSetting(patternSettingDialog.pattern(), ""), indexOfSelectedItem)
            prefixList.updateUI()
        }
    }
}
