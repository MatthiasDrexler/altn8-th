package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.RelatedFilesListCellRenderer
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
        popupContentModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        popupContentModel.selectionModel = RelatedFilesSelectionModel(popupContent)
        popupContentModel.cellRenderer = RelatedFilesListCellRenderer(editorWidth)
        popupContentModel.addListSelectionListener {
            val selectedIndices = popupContentModel.selectedIndices

            // Perform actions for each selected item
            selectedIndices.forEach { index ->
                val selectedItem = popupContentModel.model.getElementAt(index)
                if (selectedItem is FileCell) {
                    // Invoke navigateToFile callback for each selected item
                    navigateToFile().invoke(selectedItem)
                }
            }
        }

        JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(popupContentModel, null)
            .setTitle(TITLE)
            .setAdText(ACTION_DESCRIPTION)
            .setResizable(true)
            .setMovable(true)
            .setRequestFocus(true)
            .createPopup()
            .showInBestPositionFor(editor)
    }

    private fun navigateToFile(): (AbstractCell) -> Unit =
        { cell ->
            if (cell is FileCell) {
                cell.psiFile.navigate(true)
            }
        }

    private fun navigateToFiles(): (Set<AbstractCell>) -> Unit =
        { cell ->
            cell.forEach { navigateToFile().invoke(it) }
        }
}
