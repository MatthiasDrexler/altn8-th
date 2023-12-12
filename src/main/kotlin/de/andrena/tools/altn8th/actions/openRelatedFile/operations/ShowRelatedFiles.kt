package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import de.andrena.tools.altn8th.actions.openRelatedFile.ui.RelatedFilesListCellRenderer
import de.andrena.tools.altn8th.adapter.Navigation
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
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

        val psiFiles = prioritizedRelations.relations.map { it.relatedFile }
            .mapNotNull { FileConverter().toVirtualFile(it) }
            .mapNotNull { JetBrainsPsiFile().findFor(it, project) }

        val editorWidth = editor.scrollingModel.visibleArea.width

        JBPopupFactory
            .getInstance()
            .createPopupChooserBuilder(psiFiles)
            .setTitle("Related Files")
            .setAdText("The selected file will be opened in the editor")
            .setRenderer(RelatedFilesListCellRenderer(editorWidth))
            .createPopup()
            .showInBestPositionFor(editor)
    }
}
