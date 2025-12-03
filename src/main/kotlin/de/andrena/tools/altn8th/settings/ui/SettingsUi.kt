package de.andrena.tools.altn8th.settings.ui

import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.components.general.GeneralLinksUi
import de.andrena.tools.altn8th.settings.ui.components.general.GeneralSettingsUi
import javax.swing.JPanel


class SettingsUi(settingsState: SettingsState) : Ui {
    private val generalSettingsUi = GeneralSettingsUi(settingsState)
    private val generalLinksUi = GeneralLinksUi()

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(generalSettingsUi.panel, 0)
        .addComponentFillVertically(generalLinksUi.panel, 0)
        .panel

    override fun isModified() = generalSettingsUi.isModified()

    override fun apply() = generalSettingsUi.apply()

    override fun reset() = generalSettingsUi.reset()
}
