package de.andrena.tools.altn8th.settings.ui.components.filenameRegex

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.SettingDialog
import javax.swing.JComponent


class FilenameRegexSettingDialog(
    currentOrigin: String = DEFAULT_ORIGIN,
    currentRelated: String = DEFAULT_RELATED,
    currentCategory: String = DEFAULT_CATEGORY
) : SettingDialog() {
    companion object {
        private val TITLE = I18n.lazyMessage("altn8.filenameRegex.add.dialog.title")
        private val ORIGIN_LABEL = I18n.lazyMessage("altn8.filenameRegex.add.dialog.origin.label")
        private val RELATED_LABEL = I18n.lazyMessage("altn8.filenameRegex.add.dialog.related.label")
        private val CATEGORY_LABEL = I18n.lazyMessage("altn8.filenameRegex.add.dialog.category.label")

        private val ALL_FIELDS_REQUIRED = I18n.lazyMessage("altn8.ui.validation.fields.all.empty")
        private val INFORMATION_REQUIRED = I18n.lazyMessage("altn8.ui.validation.field.empty")

        private val SHORT_FILENAME_REGEX_DIALOG_PURPOSE = I18n.lazyMessage("altn8.filenameRegex.add.dialog.purpose")
        private val FURTHER_INFORMATION_ABOUT_A_FILENAME_REGEX =
            I18n.lazyMessage("altn8.filenameRegex.add.dialog.furtherInformation")

        private val DEFAULT_ORIGIN = I18n.message("altn8.filenameRegex.add.default.origin")
        private val DEFAULT_RELATED = I18n.message("altn8.filenameRegex.add.default.related")
        private val DEFAULT_CATEGORY = I18n.message("altn8.filenameRegex.add.default.category")
    }

    override val headline = TITLE.get()
    override val shortPurpose = SHORT_FILENAME_REGEX_DIALOG_PURPOSE.get()
    override val furtherInformation = FURTHER_INFORMATION_ABOUT_A_FILENAME_REGEX.get()

    private val originTextFieldLabel = JBLabel(ORIGIN_LABEL.get())
    private val originTextField = JBTextField()

    private val relatedLabel = JBLabel(RELATED_LABEL.get())
    private val relatedTextField = JBTextField()

    private val categoryLabel = JBLabel(CATEGORY_LABEL.get())
    private val categoryTextField = JBTextField()

    init {
        originTextField.text = currentOrigin
        relatedTextField.text = currentRelated
        categoryTextField.text = currentCategory

        initialize()
    }

    override fun createCenterPanel(): JComponent? = FormBuilder.createFormBuilder()
        .addLabeledComponent(originTextFieldLabel, originTextField)
        .addLabeledComponent(relatedLabel, relatedTextField)
        .addLabeledComponent(categoryLabel, categoryTextField)
        .panel

    fun origin(): String = originTextField.text
    fun related(): String = relatedTextField.text
    fun category(): String = categoryTextField.text

    override fun doValidate(): ValidationInfo? {
        if (isValid()) {
            isOKActionEnabled = true
            return null
        }

        isOKActionEnabled = false
        return sequenceOf(
            originTextField.mustBeFilledIn(INFORMATION_REQUIRED.get()),
            relatedTextField.mustBeFilledIn(INFORMATION_REQUIRED.get()),
            categoryTextField.mustBeFilledIn(INFORMATION_REQUIRED.get()),
            ValidationInfo(ALL_FIELDS_REQUIRED.get())
        )
            .filterNotNull()
            .first()
    }

    private fun isValid(): Boolean =
        originTextField.text.isNotBlank()
            && relatedTextField.text.isNotBlank()
            && categoryTextField.text.isNotBlank()

    private fun JBTextField.mustBeFilledIn(errorHint: String): ValidationInfo? =
        if (this.text.isNotBlank()) null else ValidationInfo(errorHint, this)
}
