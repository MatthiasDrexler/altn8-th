package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.RelatedFilesListCellRenderer
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.FileCell


internal class ShowRelatedFiles(
    private val popupContent: PopupContent,
    private val editor: Editor
) {
    fun popUp() {
        val editorWidth = editor.scrollingModel.visibleArea.width

        val list = JBList(popupContent.cells())
        list.selectionModel = RelatedFilesSelectionModel()

        JBPopupFactory
            .getInstance()
            .createPopupChooserBuilder(popupContent.cells())
            .setTitle("Related Files")
            .setAdText("The selected file will be opened in the editor")
            .setRenderer(RelatedFilesListCellRenderer(editorWidth))
            .setItemChosenCallback(navigateToFileRepresentedBy())
            .createPopup()
            .showInBestPositionFor(editor)
    }

    private fun navigateToFileRepresentedBy(): (t: AbstractCell) -> Unit =
        { cell ->
            if (cell is FileCell) {
                cell.psiFile.navigate(true)
            }
        }
}
