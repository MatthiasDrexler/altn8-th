package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.domain.settings.SettingsState
import javax.swing.JComponent
import javax.swing.JPanel


class SettingsUi(settingsState: SettingsState) {
    val panel: JPanel

    private val enabledStatusCheckBox = JBCheckBox("Enabled? ", settingsState.isEnabled)

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(enabledStatusCheckBox, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = enabledStatusCheckBox

    var enabledStatus: Boolean
        get() = enabledStatusCheckBox.isSelected
        set(newStatus) {
            enabledStatusCheckBox.isSelected = newStatus
        }
}
