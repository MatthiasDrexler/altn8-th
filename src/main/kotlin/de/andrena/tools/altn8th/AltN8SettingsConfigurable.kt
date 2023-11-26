package de.andrena.tools.altn8th

import com.intellij.openapi.options.Configurable
import de.andrena.tools.altn8th.domain.settings.AltN8Settings
import de.andrena.tools.altn8th.ui.settings.AltN8SettingsPanel
import javax.swing.JComponent

class AltN8SettingsConfigurable : Configurable {
    private lateinit var settingsComponent: AltN8SettingsPanel

    override fun getDisplayName(): String {
        return "AltN8-TH"
    }

    override fun createComponent(): JComponent {
        settingsComponent = AltN8SettingsPanel()
        return settingsComponent.panel
    }

    override fun isModified(): Boolean {
        val altN8SettingsState = AltN8Settings.getInstance().state
        return altN8SettingsState.isEnabled != settingsComponent.enabledStatus
    }

    override fun apply() {
        val altN8SettingsState = AltN8Settings.getInstance().state
        altN8SettingsState.isEnabled = settingsComponent.enabledStatus
    }
}