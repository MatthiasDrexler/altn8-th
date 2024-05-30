package de.andrena.tools.altn8th.settings

import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.components.patterns.prefix.PrefixSettingsUi

private val NAME = I18n.lazyMessage("altn8.pattern.prefixes")

class PrefixConfigurable : AbstractConfigurable() {
    override fun getDisplayName(): String = NAME.get()
    override fun createUi(settingsState: SettingsState): Ui = PrefixSettingsUi(settingsState)
}
