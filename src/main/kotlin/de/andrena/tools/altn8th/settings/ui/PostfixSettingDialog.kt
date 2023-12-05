package de.andrena.tools.altn8th.settings.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent

internal class PostfixSettingDialog : DialogWrapper(true) {
    private val patternTextField = JBTextField("Pattern")

    init {
        title = "Postfix"
        init()
    }

    override fun createCenterPanel(): JComponent? = FormBuilder.createFormBuilder()
        .addLabeledComponent("Pattern", patternTextField)
        .panel

    fun pattern() = patternTextField.text
}