package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user.TooltipTextUpdateListener
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user.UserClicksListener
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user.UserPressesEnterListener
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.RelatedFilesListCellRenderer
import java.awt.Dimension
import javax.swing.ListSelectionModel


internal class ShowRelatedFiles(
    private val popupContent: PopupContent,
    private val editor: Editor
) {
    companion object {
        private const val MINIMUM_WIDTH = 300
        private const val MINIMUM_HEIGHT = 0

        private const val TITLE = "Related Files"
        private const val ACTION_DESCRIPTION = "Select files to open in the editor (hold shift to open a file in a new editor group)"
    }

    fun popUp() {
        val editorWidth = editor.scrollingModel.visibleArea.width

        val popupContentModel = JBList(popupContent.cells())
        popupContentModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        popupContentModel.selectionModel = RelatedFilesSelectionModel(popupContent)
        popupContentModel.cellRenderer = RelatedFilesListCellRenderer(editorWidth)
        popupContentModel.toolTipText = TITLE
        popupContentModel.setSelectionInterval(0, 0)

        val scrollPane = JBScrollPane(popupContentModel)

        val popup = JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(scrollPane, null)
            .setMinSize(Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT))
            .setTitle(TITLE)
            .setAdText(ACTION_DESCRIPTION)
            .setResizable(true)
            .setMovable(true)
            .setRequestFocus(true)
            .createPopup()

        popupContentModel.addKeyListener(UserPressesEnterListener(popup, popupContentModel))
        popupContentModel.addMouseListener(UserClicksListener(popup, popupContentModel))
        popupContentModel.addMouseMotionListener(TooltipTextUpdateListener(popupContent, popupContentModel))

        popup.showInBestPositionFor(editor)
        popupContentModel.requestFocusInWindow()
    }
}
