package de.andrena.tools.altn8th.settings.ui.components.general

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.panels.VerticalLayout
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineContextHelp
import javax.swing.JPanel

private val TITLE = I18n.lazyMessage("altn8.settings.general")
private val DESCRIPTION = I18n.lazyMessage("altn8.settings.general.description")
private val CASE_INSENSITIVE_MATCHING_LABEL = I18n.lazyMessage("altn8.settings.general.caseInsensitiveMatching")

private const val SPACING = 8

internal class GeneralSettingsUi(private val settingsState: SettingsState) : Ui {
    private val caseInsensitiveMatchingCheckBox = JBCheckBox(CASE_INSENSITIVE_MATCHING_LABEL.get(), settingsState.caseInsensitiveMatching)

    override val panel: JPanel =
        JPanel(VerticalLayout(SPACING)).apply {
            add(JBTextArea(DESCRIPTION.get()).multilineContextHelp())
            add(caseInsensitiveMatchingCheckBox)
        }

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean = caseInsensitiveMatchingCheckBox.isSelected != settingsState.caseInsensitiveMatching

    override fun apply() {
        settingsState.caseInsensitiveMatching = caseInsensitiveMatchingCheckBox.isSelected
    }

    override fun reset() {
        caseInsensitiveMatchingCheckBox.isSelected = settingsState.caseInsensitiveMatching
    }
}
