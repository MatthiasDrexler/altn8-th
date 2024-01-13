package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import javax.swing.DefaultListSelectionModel

internal class RelatedFilesSelectionModel(private val popupContent: PopupContent) : DefaultListSelectionModel() {
    override fun setSelectionInterval(startIndex: Int, endIndex: Int) {
        if (isNoMultiSelection(startIndex, endIndex) && isCategory(startIndex)) {
            setAllItemsForCategoryAt(startIndex)
            return
        }

        super.setSelectionInterval(startIndex, endIndex)
    }

    private fun setAllItemsForCategoryAt(index: Int) {
        val correspondingCells = popupContent.correspondingCellsToElementAt(index)
        if (correspondingCells.size == 2) {
            skipCategoryAndSetNextOf(index)
            return
        }

        super.setSelectionInterval(correspondingCells.min(), correspondingCells.max())
    }

    private fun skipCategoryAndSetNextOf(index: Int) {
        val nextIndex = nextIndexOnSkip(index)
        popupContent.cellAt(nextIndex)?.let { super.setSelectionInterval(nextIndex, nextIndex) }
    }

    override fun addSelectionInterval(startIndex: Int, endIndex: Int) {
        if (isNoMultiSelection(startIndex, endIndex) && isCategory(startIndex)) {
            addAllItemsForCategoryAt(startIndex)
            return
        }

        super.addSelectionInterval(startIndex, endIndex)
    }

    private fun addAllItemsForCategoryAt(index: Int) {
        val correspondingCells = popupContent.correspondingCellsToElementAt(index)
        if (correspondingCells.size == 2) {
            skipCategoryAndAddNextOf(index)
            return
        }

        super.addSelectionInterval(correspondingCells.min(), correspondingCells.max())
    }

    private fun skipCategoryAndAddNextOf(index: Int) {
        val nextIndex = nextIndexOnSkip(index)
        popupContent.cellAt(nextIndex)?.let { super.addSelectionInterval(nextIndex, nextIndex) }
    }

    private fun isNoMultiSelection(startIndex: Int, endIndex: Int) = startIndex == endIndex

    private fun isCategory(startIndex: Int) = popupContent.cellAt(startIndex) is CategoryCell

    private fun isDownMovementTo(index: Int) = super.getAnchorSelectionIndex() < index

    private fun nextIndexOnSkip(index: Int) = if (isDownMovementTo(index)) index + 1 else index - 1
}
