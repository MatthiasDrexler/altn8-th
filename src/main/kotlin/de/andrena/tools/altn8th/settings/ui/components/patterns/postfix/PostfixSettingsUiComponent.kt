package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.components.patterns.prefix.PrefixSettingDialog
import javax.swing.JPanel


internal class PostfixSettingsUiComponent(private val settingsState: SettingsState) : Ui {
    companion object {
        private const val TITLE = "Postfixes"
        private const val NO_POSTFIXES_PLACEHOLDER = "No postfixes configured yet"
    }

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
        panel.border = IdeBorderFactory.createTitledBorder(TITLE, false)

        postfixList.emptyText.setText(NO_POSTFIXES_PLACEHOLDER)
    }

    override fun isModified() = postfixListModel.items != settingsState.postfixes

    override fun apply() {
        settingsState.postfixes = postfixListModel.items
    }

    override fun reset() {
        postfixListModel.removeAll()
        settingsState.postfixes.map { postfixListModel.add(it) }
    }

    private fun onAdd() {
        val prefixSettingDialog = PrefixSettingDialog()
        if (prefixSettingDialog.showAndGet()) {
            postfixListModel.add(
                PostfixSetting(
                    prefixSettingDialog.pattern(),
                    prefixSettingDialog.description(),
                    prefixSettingDialog.category()
                )
            )
            postfixList.updateUI()
        }
    }

    private fun onEdit() {
        val indexOfSelectedItem = postfixList.selectedIndex
        if (indexOfSelectedItem < 0) {
            return
        }

        val patternSettingDialog = PostfixSettingDialog(
            postfixList.selectedValue.pattern,
            postfixList.selectedValue.description,
            postfixList.selectedValue.category
        )
        if (patternSettingDialog.showAndGet()) {
            postfixListModel.setElementAt(
                PostfixSetting(
                    patternSettingDialog.pattern(),
                    patternSettingDialog.description(),
                    patternSettingDialog.category()
                ), indexOfSelectedItem
            )
            postfixList.updateUI()
        }
    }
}
