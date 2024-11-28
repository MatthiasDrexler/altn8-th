package de.andrena.tools.altn8th.settings.ui.components.general

import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.ProjectManager
import com.intellij.ui.HyperlinkLabel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.panels.VerticalLayout
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.AbstractConfigurable
import de.andrena.tools.altn8th.settings.FreeRegexConfigurable
import de.andrena.tools.altn8th.settings.PostfixConfigurable
import de.andrena.tools.altn8th.settings.PrefixConfigurable
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineContextHelp
import javax.swing.JPanel

private val TITLE = I18n.lazyMessage("altn8.settings.patterns")
private val DESCRIPTION = I18n.lazyMessage("altn8.settings.patterns.description")

private const val SPACING = 8

internal class GeneralLinksUi : Ui {
    private val prefixSettingsLink =
        HyperlinkLabel(I18n.lazyMessage("altn8.pattern.prefixes").get()).apply {
            addHyperlinkListener {
                routeToSettingPage(PrefixConfigurable::class.java)
            }
        }

    private val postfixSettingsLink =
        HyperlinkLabel(I18n.lazyMessage("altn8.pattern.postfixes").get()).apply {
            addHyperlinkListener {
                routeToSettingPage(PostfixConfigurable::class.java)
            }
        }

    private val freeRegexSettingsLink =
        HyperlinkLabel(I18n.lazyMessage("altn8.freeRegexes").get()).apply {
            addHyperlinkListener {
                routeToSettingPage(FreeRegexConfigurable::class.java)
            }
        }

    override val panel: JPanel =
        JPanel(VerticalLayout(SPACING)).apply {
            add(JBTextArea(DESCRIPTION.get()).multilineContextHelp())
            add(prefixSettingsLink)
            add(postfixSettingsLink)
            add(freeRegexSettingsLink)
        }

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean = false

    override fun apply() {
    }

    override fun reset() {
    }

    private fun routeToSettingPage(target: Class<out AbstractConfigurable>) {
        ShowSettingsUtil.getInstance()
            .showSettingsDialog(ProjectManager.getInstance().defaultProject, target)
    }
}
