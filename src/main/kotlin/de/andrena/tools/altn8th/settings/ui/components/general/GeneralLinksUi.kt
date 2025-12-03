package de.andrena.tools.altn8th.settings.ui.components.general

import com.intellij.ide.DataManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.options.ex.Settings
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.util.ActionCallback
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.ActionLink
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.panels.VerticalLayout
import de.andrena.tools.altn8th.internationalization.I18n
import de.andrena.tools.altn8th.settings.AbstractConfigurable
import de.andrena.tools.altn8th.settings.FilenameRegexConfigurable
import de.andrena.tools.altn8th.settings.PostfixConfigurable
import de.andrena.tools.altn8th.settings.PrefixConfigurable
import de.andrena.tools.altn8th.settings.ui.Ui
import de.andrena.tools.altn8th.settings.ui.multilineContextHelp
import java.awt.Component
import java.awt.event.ActionEvent
import javax.swing.JPanel
import kotlin.reflect.KClass


class GeneralLinksUi : Ui {
    companion object {
        private val TITLE = I18n.lazyMessage("altn8.settings.patterns")
        private val DESCRIPTION = I18n.lazyMessage("altn8.settings.patterns.description")
        private const val SPACING = 8
    }

    private val prefixSettingsLink =
        ActionLink(I18n.lazyMessage("altn8.pattern.prefixes").get()) {
            openSettingPage(it, PrefixConfigurable::class)
        }

    private val postfixSettingsLink =
        ActionLink(I18n.lazyMessage("altn8.pattern.postfixes").get()) {
            openSettingPage(it, PostfixConfigurable::class)
        }

    private val filenameRegexSettingsLink =
        ActionLink(I18n.lazyMessage("altn8.filenameRegexes").get()) {
            openSettingPage(it, FilenameRegexConfigurable::class)
        }

    override val panel: JPanel =
        JPanel(VerticalLayout(SPACING)).apply {
            add(JBTextArea(DESCRIPTION.get()).multilineContextHelp())
            add(prefixSettingsLink)
            add(postfixSettingsLink)
            add(filenameRegexSettingsLink)
        }

    init {
        panel.border = IdeBorderFactory.createTitledBorder(TITLE.get(), false)
    }

    override fun isModified(): Boolean = false

    override fun apply() {}

    override fun reset() {}

    private fun openSettingPage(event: ActionEvent, target: KClass<out AbstractConfigurable>) {
        val sourceComponent = event.source as Component
        val dataContext = DataManager.getInstance().getDataContext(sourceComponent)
        val settingsDialog = Settings.KEY.getData(dataContext)

        settingsDialog?.let {
            routeTo(it, target)
        } ?: openSettingsDialog(target)
    }

    private fun routeTo(it: Settings, target: KClass<out AbstractConfigurable>): ActionCallback {
        val targetInstance = it.find(target.qualifiedName.toString())
        return it.select(targetInstance)
    }

    private fun openSettingsDialog(target: KClass<out AbstractConfigurable>) {
        ShowSettingsUtil.getInstance().showSettingsDialog(ProjectManager.getInstance().defaultProject, target.java)
    }
}
