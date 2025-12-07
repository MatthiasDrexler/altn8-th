package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.user

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell.AbstractCell
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class UserPressesEnterListener(
    private val popup: JBPopup,
    private val popupContentModel: JBList<AbstractCell>
) : KeyAdapter() {
    override fun keyPressed(keyEvent: KeyEvent) {
        if (keyEvent.keyCode == KeyEvent.VK_ENTER) {
            popupContentModel.selectedIndices
                .map { popupContentModel.model.getElementAt(it) }
                .forEach(navigateToFile())
            popup.closeOk(keyEvent)
        }
    }
}
