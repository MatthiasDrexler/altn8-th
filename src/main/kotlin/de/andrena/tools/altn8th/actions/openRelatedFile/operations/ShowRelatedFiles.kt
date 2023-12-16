package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.RelatedFilesListCellRenderer
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.user.UserClicksListener
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.user.UserPressesEnterListener
import javax.swing.ListSelectionModel


internal class ShowRelatedFiles(
    private val popupContent: PopupContent,
    private val editor: Editor
) {
    companion object {
        private const val TITLE = "Related Files"
        private const val ACTION_DESCRIPTION = "The selected files will be opened in the editor"
    }

    fun popUp() {
        val editorWidth = editor.scrollingModel.visibleArea.width

        val popupContentModel = JBList(popupContent.cells())
        popupContentModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        popupContentModel.selectionModel = RelatedFilesSelectionModel(popupContent)
        popupContentModel.cellRenderer = RelatedFilesListCellRenderer(editorWidth)

        val popup = JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(popupContentModel, null)
            .setTitle(TITLE)
            .setAdText(ACTION_DESCRIPTION)
            .setResizable(true)
            .setMovable(true)
            .setRequestFocus(true)
            .createPopup()
        popup.showInBestPositionFor(editor)

        popupContentModel.addKeyListener(UserPressesEnterListener(popup, popupContentModel))
        popupContentModel.addMouseListener(UserClicksListener(popup, popupContentModel))
    }
}
