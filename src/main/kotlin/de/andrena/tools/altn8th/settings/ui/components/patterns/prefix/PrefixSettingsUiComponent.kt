package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import de.andrena.tools.altn8th.settings.ui.Ui
import javax.swing.JPanel


internal class PrefixSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Prefixes"
        private const val NO_PREFIXES_PLACEHOLDER = "No prefixes configured yet"
    }

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
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)

        prefixList.emptyText.setText(NO_PREFIXES_PLACEHOLDER)
    }

    override fun isModified() = prefixListModel.items != settingsState.prefixes

    override fun apply() {
        settingsState.prefixes = prefixListModel.items
    }

    override fun reset() {
        prefixListModel.removeAll()
        settingsState.prefixes.map { prefixListModel.add(it) }
    }

    private fun onAdd() {
        val prefixSettingDialog = PrefixSettingDialog()
        if (prefixSettingDialog.showAndGet()) {
            prefixListModel.add(
                PrefixSetting(
                    prefixSettingDialog.pattern(),
                    prefixSettingDialog.description(),
                    prefixSettingDialog.category()
                )
            )
            prefixList.updateUI()
        }
    }

    private fun onEdit() {
        val indexOfSelectedItem = prefixList.selectedIndex
        if (indexOfSelectedItem < 0) {
            return
        }

        val patternSettingDialog = PrefixSettingDialog(
            prefixList.selectedValue.pattern,
            prefixList.selectedValue.description,
            prefixList.selectedValue.category
        )
        if (patternSettingDialog.showAndGet()) {
            prefixListModel.setElementAt(
                PrefixSetting(
                    patternSettingDialog.pattern(),
                    patternSettingDialog.description(),
                    patternSettingDialog.category()
                ),
                indexOfSelectedItem
            )
            prefixList.updateUI()
        }
    }
}
