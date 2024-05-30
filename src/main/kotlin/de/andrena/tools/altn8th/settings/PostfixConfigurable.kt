package de.andrena.tools.altn8th.settings

import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.components.patterns.postfix.PostfixSettingsUi

class PostfixConfigurable : AbstractConfigurable() {
    companion object {
        private const val NAME = "Postfixes";
    }

    override fun getDisplayName(): String = NAME

    override fun createUi(settingsState: SettingsState): Ui = PostfixSettingsUi(settingsState)
}
