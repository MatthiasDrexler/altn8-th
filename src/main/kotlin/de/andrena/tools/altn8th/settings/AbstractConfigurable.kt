package de.andrena.tools.altn8th.settings

import com.intellij.openapi.options.Configurable
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.Ui
import javax.swing.JComponent

abstract class AbstractConfigurable : Configurable {
    private lateinit var settingsUi: Ui

    protected abstract fun createUi(settingsState: SettingsState): Ui

    override fun createComponent(): JComponent {
        val settingsState = SettingsPersistentStateComponent.getInstance().state
        settingsUi = createUi(settingsState)
        return settingsUi.panel
    }

    override fun isModified() = settingsUi.isModified()

    override fun apply() = settingsUi.apply()

    override fun reset() = settingsUi.reset()
}
