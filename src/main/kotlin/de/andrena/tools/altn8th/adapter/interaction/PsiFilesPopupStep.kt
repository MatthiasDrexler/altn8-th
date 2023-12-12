package de.andrena.tools.altn8th.adapter.interaction

import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.popup.ListPopupStep
import com.intellij.openapi.ui.popup.ListSeparator
import com.intellij.openapi.ui.popup.MnemonicNavigationFilter
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.SpeedSearchFilter
import com.intellij.psi.NavigatablePsiElement
import javax.swing.Icon

internal class PsiFilesPopupStep(
    private val prioritizedRelations: Collection<NavigatablePsiElement>
) : ListPopupStep<NavigatablePsiElement> {
    companion object {
        private const val TITLE = "Select the Related File to Go To"
    }

    override fun getTitle(): String = TITLE

    override fun canceled() {
        // do nothing
    }

    override fun isMnemonicsNavigationEnabled(): Boolean = false

    override fun getMnemonicNavigationFilter(): MnemonicNavigationFilter<NavigatablePsiElement>? = null

    override fun isSpeedSearchEnabled(): Boolean = false

    override fun getSpeedSearchFilter(): SpeedSearchFilter<NavigatablePsiElement>? = null

    override fun isAutoSelectionEnabled(): Boolean = false

    override fun getFinalRunnable(): Runnable? = null

    override fun getValues(): MutableList<NavigatablePsiElement> = prioritizedRelations.toMutableList()

    override fun getDefaultOptionIndex(): Int = 0

    override fun getSeparatorAbove(relation: NavigatablePsiElement?): ListSeparator = ListSeparator("test")

    override fun getTextFor(relation: NavigatablePsiElement?): String =
        """
        | ${relation?.name}
        """
            .trimMargin()

    override fun getIconFor(value: NavigatablePsiElement?): Icon = AllIcons.Nodes.Interface

    override fun isSelectable(value: NavigatablePsiElement?): Boolean = true

    override fun hasSubstep(selectedValue: NavigatablePsiElement?): Boolean = false

    override fun onChosen(selectedValue: NavigatablePsiElement?, finalChoice: Boolean): PopupStep<*>? {
        selectedValue?.navigate(true)
        return PopupStep.FINAL_CHOICE
    }
}
