package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.RelatedFilesListCellRenderer
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.FileCell
import javax.swing.ListSelectionModel


internal class ShowRelatedFiles(
    private val popupContent: PopupContent,
    private val editor: Editor
) {
    companion object {
        private const val TITLE = "Related Files"
        private const val ACTION_DESCRIPTION = "The selected file will be opened in the editor"
    }

    fun popUp() {
        val editorWidth = editor.scrollingModel.visibleArea.width

        val popupContentModel = JBList(popupContent.cells())
        popupContentModel.selectionModel = RelatedFilesSelectionModel()
        popupContentModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION

        JBPopupFactory
            .getInstance()
            .createPopupChooserBuilder(popupContent.cells())
            .setTitle(TITLE)
            .setAdText(ACTION_DESCRIPTION)
            .setRenderer(RelatedFilesListCellRenderer(editorWidth))
            .setItemChosenCallback(navigateToFileRepresentedBy())
            .createPopup()
            .showInBestPositionFor(editor)
    }

    private fun navigateToFileRepresentedBy(): (AbstractCell) -> Unit =
        { cell ->
            if (cell is FileCell) {
                cell.psiFile.navigate(true)
            }
        }
}
