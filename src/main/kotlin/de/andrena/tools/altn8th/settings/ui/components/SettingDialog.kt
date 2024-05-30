package de.andrena.tools.altn8th.settings.ui.components

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.FormBuilder
import de.andrena.tools.altn8th.settings.ui.bold
import de.andrena.tools.altn8th.settings.ui.multilineLabel
import javax.swing.JComponent

internal abstract class SettingDialog : DialogWrapper(false) {
    companion object {
        private const val VERTICAL_SPACING_AFTER_PURPOSE = 8
        private const val VERTICAL_SPACING_AFTER_FURTHER_INFORMATION = 6
    }

    protected abstract val headline: String
    protected abstract val shortPurpose: String
    protected abstract val furtherInformation: String

    protected fun initialize() {
        super.setTitle(this.headline)
        init()
    }

    override fun createTitlePane(): JComponent = FormBuilder.createFormBuilder()
        .addComponent(JBLabel(shortPurpose).bold())
        .addVerticalGap(VERTICAL_SPACING_AFTER_PURPOSE)
        .panel

    override fun createNorthPanel(): JComponent = FormBuilder.createFormBuilder()
        .addComponent(JBTextArea(furtherInformation).multilineLabel())
        .addVerticalGap(VERTICAL_SPACING_AFTER_FURTHER_INFORMATION)
        .panel
}
