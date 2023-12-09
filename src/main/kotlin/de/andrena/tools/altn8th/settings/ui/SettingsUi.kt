package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.JBSplitter
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.components.PostfixSettingsUiComponent
import de.andrena.tools.altn8th.settings.ui.components.PrefixSettingsUiComponent
import javax.swing.JPanel


internal class SettingsUi(settingsState: SettingsState) : Ui {
    private val prefixSettingsUiComponent = PrefixSettingsUiComponent(settingsState)
    private val postfixSettingsUiComponent = PostfixSettingsUiComponent(settingsState)
    private val patternSettingsSplitter = JBSplitter(false, 0.5f)

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(patternSettingsSplitter, 0)
        .panel

    init {
        patternSettingsSplitter.firstComponent = prefixSettingsUiComponent.panel
        patternSettingsSplitter.secondComponent = postfixSettingsUiComponent.panel
    }

    override fun isModified() =
        prefixSettingsUiComponent.isModified() || postfixSettingsUiComponent.isModified()


    override fun apply() {
        prefixSettingsUiComponent.apply()
        postfixSettingsUiComponent.apply()
    }
}
