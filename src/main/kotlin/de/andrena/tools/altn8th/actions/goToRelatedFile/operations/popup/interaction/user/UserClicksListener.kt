package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.user

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell.AbstractCell
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class UserClicksListener(
    private val popup: JBPopup,
    private val popupContentModel: JBList<AbstractCell>
) : MouseAdapter() {
    override fun mouseClicked(mouseEvent: MouseEvent) {
        if (singleClickWithMainMouseButton(mouseEvent) && shiftIsUnpressed(mouseEvent)) {
            popupContentModel.selectedIndices
                .map { popupContentModel.model.getElementAt(it) }
                .forEach(navigateToFile())
            popup.closeOk(mouseEvent)
        }
    }

    private fun singleClickWithMainMouseButton(e: MouseEvent) = e.clickCount == 1 && e.button == MouseEvent.BUTTON1

    private fun shiftIsUnpressed(mouseEvent: MouseEvent) = !mouseEvent.isShiftDown
}
