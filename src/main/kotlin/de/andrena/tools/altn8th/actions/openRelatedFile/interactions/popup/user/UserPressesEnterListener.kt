package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.user

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

internal class UserPressesEnterListener(
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
