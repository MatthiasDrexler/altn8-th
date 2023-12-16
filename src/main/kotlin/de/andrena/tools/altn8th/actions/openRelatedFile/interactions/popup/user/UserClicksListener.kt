package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.user

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

internal class UserClicksListener(
    private val popup: JBPopup,
    private val popupContentModel: JBList<AbstractCell>
) : MouseAdapter() {
    override fun mouseClicked(mouseEvent: MouseEvent) {
        if (singleClickWithMainMouseButton(mouseEvent)) {
            popupContentModel.selectedIndices
                .map { popupContentModel.model.getElementAt(it) }
                .forEach(openFileInCurrentEditor())
            popup.closeOk(mouseEvent)
        }
    }

    private fun singleClickWithMainMouseButton(e: MouseEvent) = e.clickCount == 1 && e.button == MouseEvent.BUTTON1

    private fun openFileInCurrentEditor(): (AbstractCell) -> Unit =
        { cell ->
            if (cell is FileCell) {
                cell.psiFile.navigate(true)
            }
        }
}
