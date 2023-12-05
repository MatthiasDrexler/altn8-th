package de.andrena.tools.altn8th.settings.ui.dialog

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent

internal class PatternSettingDialog() : DialogWrapper(true) {
    private val patternTextField: JBTextField = JBTextField()

    constructor(currentPattern: String?) : this() {
        patternTextField.text = currentPattern
    }

    init {
        title = "Postfix"
        init()
    }

    override fun createCenterPanel(): JComponent? = FormBuilder.createFormBuilder()
        .addLabeledComponent("Pattern", patternTextField)
        .panel

    fun pattern() = patternTextField.text
}