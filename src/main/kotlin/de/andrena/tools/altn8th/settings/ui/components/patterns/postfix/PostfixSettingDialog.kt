package de.andrena.tools.altn8th.settings.ui.components.patterns.postfix

import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.components.patterns.PatternSettingDialog

private val TITLE = I18n.lazyMessage("altn8.pattern.postfix.add.dialog.title")
private val SHORT_POSTFIX_DIALOG_PURPOSE = I18n.lazyMessage("altn8.pattern.postfix.add.dialog.purpose")
private val FURTHER_INFORMATION_ABOUT_A_POSTFIX =
    I18n.lazyMessage("altn8.pattern.postfix.add.dialog.furtherInformation")

private val DEFAULT_PATTERN = I18n.message("altn8.pattern.postfix.add.default.pattern")
private val DEFAULT_DESCRIPTION = I18n.message("altn8.pattern.postfix.add.default.description")
private val DEFAULT_CATEGORY = I18n.message("altn8.pattern.postfix.add.default.category")

internal class PostfixSettingDialog(
    currentPattern: String = DEFAULT_PATTERN,
    currentDescription: String = DEFAULT_DESCRIPTION,
    currentCategory: String = DEFAULT_CATEGORY
) : PatternSettingDialog(currentPattern, currentDescription, currentCategory) {
    override val headline = TITLE.get()
    override val shortPurpose = SHORT_POSTFIX_DIALOG_PURPOSE.get()
    override val furtherInformation = FURTHER_INFORMATION_ABOUT_A_POSTFIX.get()

    init {
        initialize()
    }
}
