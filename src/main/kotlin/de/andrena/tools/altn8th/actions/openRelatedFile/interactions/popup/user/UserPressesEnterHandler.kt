package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.user

import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

internal class UserPressesEnterHandler(
    private val popup: JBPopup,
    private val popupContentModel: JBList<AbstractCell>
) : KeyAdapter() {
    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_ENTER) {
            val selectedIndices = popupContentModel.selectedIndices
            selectedIndices.forEach {
                val selectedItem = popupContentModel.model.getElementAt(it)
                if (selectedItem is FileCell) {
                    openFileInCurrentEditor().invoke(selectedItem)
                    popup.closeOk(null)
                }
            }
        }
    }

    private fun openFileInCurrentEditor(): (AbstractCell) -> Unit =
        { cell ->
            if (cell is FileCell) {
                cell.psiFile.navigate(true)
            }
        }
}
