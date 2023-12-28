package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.PopupContent
import javax.swing.DefaultListSelectionModel

internal class RelatedFilesSelectionModel(private val popupContent: PopupContent) : DefaultListSelectionModel() {
    override fun setSelectionInterval(startIndex: Int, endIndex: Int) {
        if (startIndex == endIndex) {
            val correspondingCells = popupContent.correspondingCellsToElementAt(startIndex)
            super.setSelectionInterval(correspondingCells.min(), correspondingCells.max())
            return
        }

        super.setSelectionInterval(startIndex, endIndex)
    }

    override fun addSelectionInterval(startIndex: Int, endIndex: Int) {
        if (startIndex == endIndex) {
            val correspondingCells = popupContent.correspondingCellsToElementAt(startIndex)
            super.addSelectionInterval(correspondingCells.min(), correspondingCells.max())
            return
        }

        super.addSelectionInterval(startIndex, endIndex)
    }
}
