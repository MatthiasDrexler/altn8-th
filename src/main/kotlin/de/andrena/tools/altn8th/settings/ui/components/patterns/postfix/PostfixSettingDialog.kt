package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternSettingDialog


internal class PostfixSettingDialog(
    currentPattern: String = """[\w]*[Pp]ostfix""",
    currentDescription: String = """Files ending on postfix""",
    currentCategory: String = """Custom"""
) : PatternSettingDialog(currentPattern, currentDescription, currentCategory) {
    companion object {
        private const val TITLE = "Postfix Setting"

        private val shortDialogPurposeDescription =
            """
            | Specify a postfix pattern, which associates relating files.
            """
                .trimMargin()

        private val furtherInformationAboutAPrefix =
            """
            | The regex pattern defining a postfix should start with a distinctive pattern.
            """
                .trimMargin()
    }

    override val dialogPurposeLabel = shortDialogPurposeDescription
    override val furtherInformationLabel = furtherInformationAboutAPrefix

    init {
        title = TITLE
        init()
    }
}
