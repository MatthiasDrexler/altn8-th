package de.andrena.tools.altn8th.settings

import com.intellij.openapi.options.Configurable
import de.andrena.tools.altn8th.settings.ui.PostfixSettingsUi
import javax.swing.JComponent

class PostfixConfigurable : Configurable {
    private lateinit var postfixSettingsUi: PostfixSettingsUi

    override fun getDisplayName(): String {
        return "AltN8-TH"
    }

    override fun createComponent(): JComponent {
        val settingsState = SettingsPersistentStateComponent.getInstance().state
        postfixSettingsUi = PostfixSettingsUi(settingsState)
        return postfixSettingsUi.panel
    }

    override fun isModified(): Boolean = postfixSettingsUi.isModified()

    override fun apply() = postfixSettingsUi.apply()
}