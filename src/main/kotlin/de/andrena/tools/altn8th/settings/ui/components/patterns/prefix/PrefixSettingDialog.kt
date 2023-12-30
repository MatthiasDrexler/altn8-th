package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.settings.ui.bold
import javax.swing.JComponent


internal class PrefixSettingDialog() : DialogWrapper(true) {
    companion object {
        private val shortDialogPurposeDescription =
            """
            | Specify a prefix pattern, which associates relating files.
            """
                .trimMargin()

        private val furtherInformationAboutAPrefix =
            """
            | The regex pattern defining a prefix should end with a distinctive pattern.
            """
                .trimMargin()

        private const val TITLE = "Prefix Setting"

        private const val PATTERN_LABEL = "Pattern: "
        private const val DESCRIPTION_LABEL = "Description: "
        private const val CATEGORY_LABEL = "Category: "

        private const val VERTICAL_SPACING_AFTER_PURPOSE = 8
        private const val VERTICAL_SPACING_AFTER_FURTHER_INFORMATION = 6
    }

    private val dialogPurposeLabel = JBLabel(shortDialogPurposeDescription).bold()
    private val furtherInformationLabel = JBLabel(furtherInformationAboutAPrefix)

    private val patternTextFieldLabel = JBLabel(PATTERN_LABEL)
    private val patternTextField = JBTextField()

    private val descriptionLabel = JBLabel(DESCRIPTION_LABEL)
    private val descriptionTextField = JBTextField()

    private val categoryLabel = JBLabel(CATEGORY_LABEL)
    private val categoryTextField = JBTextField()

    constructor(currentPattern: String, currentDescription: String, currentCategory: String) : this() {
        patternTextField.text = currentPattern
        descriptionTextField.text = currentDescription
        categoryTextField.text = currentCategory
    }

    init {
        title = TITLE
        init()
    }


    override fun createTitlePane(): JComponent = FormBuilder.createFormBuilder()
        .addComponent(dialogPurposeLabel)
        .addVerticalGap(VERTICAL_SPACING_AFTER_PURPOSE)
        .panel

    override fun createNorthPanel(): JComponent = FormBuilder.createFormBuilder()
        .addComponent(furtherInformationLabel)
        .addVerticalGap(VERTICAL_SPACING_AFTER_FURTHER_INFORMATION)
        .panel

    override fun createCenterPanel(): JComponent? = FormBuilder.createFormBuilder()
        .addLabeledComponent(patternTextFieldLabel, patternTextField)
        .addLabeledComponent(descriptionLabel, descriptionTextField)
        .addLabeledComponent(categoryLabel, categoryTextField)
        .panel

    fun pattern(): String = patternTextField.text
    fun description(): String = descriptionTextField.text
    fun category(): String = categoryTextField.text
}
