package de.andrena.tools.altn8th.adapter.interaction

import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.ListPopupStep
import com.intellij.openapi.ui.popup.ListSeparator
import com.intellij.openapi.ui.popup.MnemonicNavigationFilter
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.SpeedSearchFilter
import de.andrena.tools.altn8th.adapter.Navigation
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations
import javax.swing.Icon

internal class FileSelectionPopupStep(
    private val prioritizedRelations: PrioritizedRelations,
    private val project: Project
) : ListPopupStep<Relation> {
    private val onChosenHandler = Navigation(project).openFile

    companion object {
        private const val TITLE = "Select the Related File to Go To"
    }

    override fun getTitle(): String = TITLE

    override fun canceled() {
        // do nothing
    }

    override fun isMnemonicsNavigationEnabled(): Boolean = false

    override fun getMnemonicNavigationFilter(): MnemonicNavigationFilter<Relation>? = null

    override fun isSpeedSearchEnabled(): Boolean = false

    override fun getSpeedSearchFilter(): SpeedSearchFilter<Relation>? = null

    override fun isAutoSelectionEnabled(): Boolean = false

    override fun getFinalRunnable(): Runnable? = null

    override fun getValues(): MutableList<Relation> = prioritizedRelations.relations.toMutableList()

    override fun getDefaultOptionIndex(): Int = 0

    override fun getSeparatorAbove(relation: Relation?): ListSeparator = ListSeparator("test")

    override fun getTextFor(relation: Relation?): String =
        """
        | ${relation?.relatedFile?.nameWithFileExtension()}
        | (${relation?.relatedFile?.relativeFrom(project.basePath ?: "")})
        """
            .trimMargin()

    override fun getIconFor(value: Relation?): Icon = AllIcons.Nodes.Interface

    override fun isSelectable(value: Relation?): Boolean = true

    override fun hasSubstep(selectedValue: Relation?): Boolean = false

    override fun onChosen(selectedValue: Relation?, finalChoice: Boolean): PopupStep<*>? {
        selectedValue?.let { onChosenHandler(it.relatedFile) }
        return PopupStep.FINAL_CHOICE
    }
}
