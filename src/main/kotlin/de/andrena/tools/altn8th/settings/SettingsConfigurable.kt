package de.andrena.tools.altn8th.settings

import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.SettingsUi
import de.andrena.tools.altn8th.settings.ui.Ui

class SettingsConfigurable : AbstractConfigurable() {
    companion object {
        private const val NAME = "AltN8-TH"
    }

    override fun getDisplayName(): String = NAME

    override fun createUi(settingsState: SettingsState): Ui = SettingsUi(settingsState)
}
