package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import de.andrena.tools.altn8th.actions.openRelatedFile.ui.popup.RelatedFilesListCellRenderer
import de.andrena.tools.altn8th.actions.openRelatedFile.ui.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.ui.popup.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.ui.popup.cell.FileCell
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


        val files: MutableList<AbstractCell> = mutableListOf(CategoryCell("category"))
        files.addAll(psiFiles.map { FileCell(it) })

        val editorWidth = editor.scrollingModel.visibleArea.width

        JBPopupFactory
            .getInstance()
            .createPopupChooserBuilder(files)
            .setTitle("Related Files")
            .setAdText("The selected file will be opened in the editor")
            .setRenderer(RelatedFilesListCellRenderer(editorWidth))
            .createPopup()
            .showInBestPositionFor(editor)
    }
}
