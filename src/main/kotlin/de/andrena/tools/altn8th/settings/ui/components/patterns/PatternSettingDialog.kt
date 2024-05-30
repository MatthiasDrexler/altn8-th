package de.andrena.tools.altn8th.settings.ui.components.patterns

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingDialog
import javax.swing.JComponent

private val PATTERN_LABEL = I18n.lazyMessage("altn8.pattern.pattern.label")
private val DESCRIPTION_LABEL = I18n.lazyMessage("altn8.pattern.description.label")
private val CATEGORY_LABEL = I18n.lazyMessage("altn8.pattern.category.label")

private val ALL_FIELDS_REQUIRED = I18n.lazyMessage("altn8.ui.validation.fields.all.empty")
private val INFORMATION_REQUIRED = I18n.lazyMessage("altn8.ui.validation.field.empty")

internal abstract class PatternSettingDialog(
    currentPattern: String,
    currentDescription: String,
    currentCategory: String
) : SettingDialog() {
    private val patternTextFieldLabel = JBLabel(PATTERN_LABEL.get())
    private val patternTextField = JBTextField()

    private val descriptionLabel = JBLabel(DESCRIPTION_LABEL.get())
    private val descriptionTextField = JBTextField()

    private val categoryLabel = JBLabel(CATEGORY_LABEL.get())
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
            patternTextField.mustBeFilledIn(INFORMATION_REQUIRED.get()),
            descriptionTextField.mustBeFilledIn(INFORMATION_REQUIRED.get()),
            categoryTextField.mustBeFilledIn(INFORMATION_REQUIRED.get()),
            ValidationInfo(ALL_FIELDS_REQUIRED.get())
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
