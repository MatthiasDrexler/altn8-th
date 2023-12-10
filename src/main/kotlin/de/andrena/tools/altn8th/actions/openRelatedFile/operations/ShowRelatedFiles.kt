package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import de.andrena.tools.altn8th.adapter.Navigation
import de.andrena.tools.altn8th.adapter.interaction.FileSelectionPopupStep
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class ShowRelatedFiles(
    private val prioritizedRelations: PrioritizedRelations,
    private val project: Project,
    private val editor: Editor
) {
    fun popUp() {
        if (prioritizedRelations.onlyOneChoice()) {
            Navigation(project).openFile(prioritizedRelations.onlyChoice().relatedFile)
            return
        }

        JBPopupFactory
            .getInstance()
            .createListPopup(FileSelectionPopupStep(prioritizedRelations, project))
            .showInBestPositionFor(editor)
    }
}
