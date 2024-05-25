package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.JBSplitter
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.settings.ui.components.freeRegex.FreeRegexSettingsUiComponent
import de.andrena.tools.altn8th.settings.ui.components.patterns.postfix.PostfixSettingsUiComponent
import de.andrena.tools.altn8th.settings.ui.components.patterns.prefix.PrefixSettingsUiComponent
import javax.swing.JPanel


internal class SettingsUi(settingsState: SettingsState) : Ui {
    private val prefixSettingsUiComponent = PrefixSettingsUiComponent(settingsState)
    private val postfixSettingsUiComponent = PostfixSettingsUiComponent(settingsState)
    private val patternSettingsSplitter = JBSplitter(false, 0.5f)
    private val freeRegexSettingsUiComponent = FreeRegexSettingsUiComponent(settingsState)
    private val freeRegexSettingsSplitter = JBSplitter(true, 0.7f)

    override val panel: JPanel = FormBuilder.createFormBuilder()
        .addComponentFillVertically(freeRegexSettingsSplitter, 0)
        .panel

    init {
        patternSettingsSplitter.firstComponent = prefixSettingsUiComponent.panel
        patternSettingsSplitter.secondComponent = postfixSettingsUiComponent.panel

        freeRegexSettingsSplitter.firstComponent = patternSettingsSplitter
        freeRegexSettingsSplitter.secondComponent = freeRegexSettingsUiComponent.panel
    }

    override fun isModified() = prefixSettingsUiComponent.isModified()
        || postfixSettingsUiComponent.isModified()
        || freeRegexSettingsUiComponent.isModified()


    override fun apply() {
        prefixSettingsUiComponent.apply()
        postfixSettingsUiComponent.apply()
        freeRegexSettingsUiComponent.apply()
    }

    override fun reset() {
        prefixSettingsUiComponent.reset()
        postfixSettingsUiComponent.reset()
        freeRegexSettingsUiComponent.reset()
    }
}
