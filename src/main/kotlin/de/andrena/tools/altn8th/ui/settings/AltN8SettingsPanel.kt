package de.andrena.tools.altn8th.ui.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel


class AltN8SettingsPanel {
    val panel: JPanel

    private val enabledStatusCheckBox = JBCheckBox("Enabled? ")

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
