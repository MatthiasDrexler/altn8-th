package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user.UserClicksListener
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user.UserPressesEnterListener
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.RelatedFilesListCellRenderer
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

        popupContentModel.addKeyListener(UserPressesEnterListener(popup, popupContentModel))
        popupContentModel.addMouseListener(UserClicksListener(popup, popupContentModel))

        popup.showInBestPositionFor(editor)
    }
}
