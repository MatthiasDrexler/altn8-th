package de.andrena.tools.altn8th.settings.ui

import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.components.PostfixSettingsUiComponent
import de.andrena.tools.altn8th.settings.ui.components.PrefixSettingsUiComponent
import javax.swing.JPanel


internal class SettingsUi(settingsState: SettingsState) : Ui {
    override val panel: JPanel

    private val prefixSettingsUiComponent = PrefixSettingsUiComponent(settingsState)

    private val postfixSettingsUiComponent = PostfixSettingsUiComponent(settingsState)

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(prefixSettingsUiComponent.panel, 0)
            .addComponent(postfixSettingsUiComponent.panel, 0)
            .panel
    }

    override fun isModified() =
        prefixSettingsUiComponent.isModified() || postfixSettingsUiComponent.isModified()


    override fun apply() {
        prefixSettingsUiComponent.apply()
        postfixSettingsUiComponent.apply()
    }
}
