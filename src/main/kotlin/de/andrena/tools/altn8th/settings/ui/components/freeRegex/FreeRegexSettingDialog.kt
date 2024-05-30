package de.andrena.tools.altn8th.settings.ui.components.freeRegex

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.settings.ui.components.SettingDialog
import javax.swing.JComponent


internal class FreeRegexSettingDialog(
    currentOrigin: String = """[Cc]urrent file's regular expression""",
    currentRelated: String = """[Rr]elated file's regular expression""",
    currentCategory: String = """Custom"""
) : SettingDialog() {
    companion object {
        private const val TITLE = "Free Regex"
        private const val PATTERN_LABEL = "Origin: "
        private const val DESCRIPTION_LABEL = "Related: "
        private const val CATEGORY_LABEL = "Category: "

        private const val ALL_FIELDS_REQUIRED = "All fields are required"
        private const val INFORMATION_REQUIRED = "Please fill in this field"

        private val shortFreeRegexDialogPurpose =
            """
            | Specify a free regex, which associates relating files.
            """
                .trimMargin()

        private val furtherInformationAboutAFreeRegex =
            """
            | If the currently active editor's filename matches the given
            | 'origin' regular expression, all files matching the given
            | 'related' regular expression are related and will be grouped by
            | the given category.
            |
            | The regular expressions are applied to the entire filename
            | including the file extension.
            """
                .trimMargin()
    }

    override val headline = TITLE
    override val shortPurpose = shortFreeRegexDialogPurpose
    override val furtherInformation = furtherInformationAboutAFreeRegex

    private val originTextFieldLabel = JBLabel(PATTERN_LABEL)
    private val originTextField = JBTextField()

    private val relatedLabel = JBLabel(DESCRIPTION_LABEL)
    private val relatedTextField = JBTextField()

    private val categoryLabel = JBLabel(CATEGORY_LABEL)
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
            originTextField.mustBeFilledIn(INFORMATION_REQUIRED),
            relatedTextField.mustBeFilledIn(INFORMATION_REQUIRED),
            categoryTextField.mustBeFilledIn(INFORMATION_REQUIRED),
            ValidationInfo(ALL_FIELDS_REQUIRED)
        )
            .filterNotNull()
            .first()
    }

    private fun isValid(): Boolean = originTextField.text.isNotBlank()
        && relatedTextField.text.isNotBlank()
        && categoryTextField.text.isNotBlank()

    private fun JBTextField.mustBeFilledIn(errorHint: String): ValidationInfo? =
        if (this.text.isNotBlank()) null else ValidationInfo(errorHint, this)
}
