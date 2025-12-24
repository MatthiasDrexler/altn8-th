package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.RelatedFilesSelectionModel
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.user.TooltipTextUpdateListener
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.user.UserClicksListener
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.user.UserPressesEnterListener
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.RelatedFilesListCellRenderer
import de.andrena.tools.altn8th.internationalization.I18n
import javax.swing.ListSelectionModel


class ShowRelatedFiles(
    private val editor: Editor
) {
    companion object {
        private val TITLE = I18n.lazyMessage("altn8.relations.popup.title")
        private val ACTION_DESCRIPTION = I18n.lazyMessage("altn8.relations.popup.action")
    }

    fun popUp(popupContent: PopupContent) {
        val editorWidth = editor.scrollingModel.visibleArea.width

        val popupContentModel = JBList(popupContent.cells())
        popupContentModel.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        popupContentModel.selectionModel = RelatedFilesSelectionModel(popupContent)
        popupContentModel.cellRenderer = RelatedFilesListCellRenderer(editorWidth)
        popupContentModel.toolTipText = TITLE.get()
        popupContentModel.setSelectionInterval(0, 0)

        val scrollPane = JBScrollPane(popupContentModel)

        val popup = JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(scrollPane, null)
            .setTitle(TITLE.get())
            .setAdText(ACTION_DESCRIPTION.get())
            .setResizable(true)
            .setMovable(true)
            .setRequestFocus(true)
            .setLocateWithinScreenBounds(true)
            .createPopup()

        popupContentModel.addKeyListener(UserPressesEnterListener(popup, popupContentModel))
        popupContentModel.addMouseListener(UserClicksListener(popup, popupContentModel))
        popupContentModel.addMouseMotionListener(TooltipTextUpdateListener(popupContent, popupContentModel))

        popup.showInBestPositionFor(editor)
        popupContentModel.requestFocusInWindow()
    }
}
