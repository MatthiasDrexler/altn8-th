package de.andrena.tools.altn8th.settings

import com.intellij.openapi.options.Configurable
import de.andrena.tools.altn8th.settings.ui.SettingsUi
import javax.swing.JComponent

class SettingsConfigurable : Configurable {
    private lateinit var settingsUi: SettingsUi

    override fun getDisplayName(): String {
        return "AltN8-TH"
    }

    override fun createComponent(): JComponent {
        val settingsState = SettingsPersistentStateComponent.getInstance().state
        settingsUi = SettingsUi(settingsState)
        return settingsUi.panel
    }

    override fun isModified() = settingsUi.isModified()

    override fun apply() = settingsUi.apply()

    override fun reset() = settingsUi.reset()
}
