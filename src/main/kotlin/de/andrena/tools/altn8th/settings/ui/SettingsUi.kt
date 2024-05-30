package de.andrena.tools.altn8th.settings.ui

import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.components.freeRegex.FreeRegexSettingsUi
import javax.swing.JPanel


internal class SettingsUi(settingsState: SettingsState) : Ui {
    private val freeRegexSettingsUi = FreeRegexSettingsUi(settingsState)

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRegexSettingsUi.panel, 0)
        .panel

    override fun isModified() = freeRegexSettingsUi.isModified()

    override fun apply() = freeRegexSettingsUi.apply()

    override fun reset() = freeRegexSettingsUi.reset()
}
