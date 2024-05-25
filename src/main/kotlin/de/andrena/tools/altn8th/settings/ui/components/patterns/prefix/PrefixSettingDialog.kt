package de.andrena.tools.altn8th.settings.ui.components.patterns.prefix

import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternSettingDialog


internal class PrefixSettingDialog(
    currentPattern: String = """[Pp]refix[\w]*""",
    currentDescription: String = """Files starting with prefix""",
    currentCategory: String = """Custom"""
) : PatternSettingDialog(currentPattern, currentDescription, currentCategory) {
    companion object {
        private const val TITLE = "Prefix Setting"

        private val shortPrefixDialogPurpose =
            """
            | Specify a prefix pattern, which associates relating files.
            """
                .trimMargin()

        private val furtherInformationAboutAPrefix =
            """
            | The regex pattern defining a prefix should end with a distinctive pattern.
            """
                .trimMargin()
    }

    override val headline = TITLE
    override val shortPurpose = shortPrefixDialogPurpose
    override val furtherInformation = furtherInformationAboutAPrefix

    init {
        initialize()
    }
}
