package de.andrena.tools.altn8th.settings.ui.components.patterns

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.settings.ui.components.SettingDialog
import javax.swing.JComponent

internal abstract class PatternSettingDialog(
    currentPattern: String,
    currentDescription: String,
    currentCategory: String
) : SettingDialog() {
    companion object {
        private const val PATTERN_LABEL = "Pattern: "
        private const val DESCRIPTION_LABEL = "Description: "
        private const val CATEGORY_LABEL = "Category: "

        private const val ALL_FIELDS_REQUIRED = "All fields are required"
        private const val INFORMATION_REQUIRED = "Please fill in this field"
    }

    private val patternTextFieldLabel = JBLabel(PATTERN_LABEL)
    private val patternTextField = JBTextField()

    private val descriptionLabel = JBLabel(DESCRIPTION_LABEL)
    private val descriptionTextField = JBTextField()

    private val categoryLabel = JBLabel(CATEGORY_LABEL)
    private val categoryTextField = JBTextField()

    init {
        patternTextField.text = currentPattern
        descriptionTextField.text = currentDescription
        categoryTextField.text = currentCategory
    }

    override fun createCenterPanel(): JComponent? = FormBuilder.createFormBuilder()
        .addLabeledComponent(patternTextFieldLabel, patternTextField)
        .addLabeledComponent(descriptionLabel, descriptionTextField)
        .addLabeledComponent(categoryLabel, categoryTextField)
        .panel

    fun pattern(): String = patternTextField.text
    fun description(): String = descriptionTextField.text
    fun category(): String = categoryTextField.text

    override fun doValidate(): ValidationInfo? {
        if (isValid()) {
            isOKActionEnabled = true
            return null
        }

        isOKActionEnabled = false
        return sequenceOf(
            patternTextField.mustBeFilledIn(INFORMATION_REQUIRED),
            descriptionTextField.mustBeFilledIn(INFORMATION_REQUIRED),
            categoryTextField.mustBeFilledIn(INFORMATION_REQUIRED),
            ValidationInfo(ALL_FIELDS_REQUIRED)
        )
            .filterNotNull()
            .first()
    }

    private fun isValid(): Boolean = patternTextField.text.isNotBlank()
        && descriptionTextField.text.isNotBlank()
        && categoryTextField.text.isNotBlank()

    private fun JBTextField.mustBeFilledIn(errorHint: String): ValidationInfo? =
        if (this.text.isNotBlank()) null else ValidationInfo(errorHint, this)
}
