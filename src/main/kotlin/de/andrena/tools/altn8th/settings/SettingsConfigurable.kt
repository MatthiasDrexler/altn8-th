package de.andrena.tools.altn8th.settings

import com.intellij.openapi.options.Configurable
import de.andrena.tools.altn8th.settings.ui.SettingsUi
import javax.swing.JComponent

class SettingsConfigurable : Configurable {
    private lateinit var settingsComponent: SettingsUi

    override fun getDisplayName(): String {
        return "AltN8-TH"
    }

    override fun createComponent(): JComponent {
        val settingsState = SettingsPersistentStateComponent.getInstance().state
        settingsComponent = SettingsUi(settingsState)
        return settingsComponent.panel
    }

    override fun isModified(): Boolean {
        val settingsState = SettingsPersistentStateComponent.getInstance().state
        return settingsState.isEnabled != settingsComponent.enabledStatus
    }

    override fun apply() {
        val settingsState = SettingsPersistentStateComponent.getInstance().state
        settingsState.isEnabled = settingsComponent.enabledStatus
    }
}