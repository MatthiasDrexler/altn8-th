package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user

import com.intellij.ui.components.JBList
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.AbstractCell
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

internal class TooltipTextUpdateListener(
    private val popupContent: PopupContent,
    private val popupContentModel: JBList<AbstractCell>
) : MouseMotionListener {
    override fun mouseDragged(mouseEvent: MouseEvent?) {
        updateTooltipText(mouseEvent)
    }

    override fun mouseMoved(mouseEvent: MouseEvent?) {
        updateTooltipText(mouseEvent)
    }

    private fun updateTooltipText(mouseEvent: MouseEvent?) {
        val mouseLocation = mouseEvent?.point
        val rowIndex = popupContentModel.locationToIndex(mouseLocation)
        val cell = popupContent.cellAt(rowIndex)
        cell?.let { popupContentModel.toolTipText = cell.tooltipText() }
    }
}
