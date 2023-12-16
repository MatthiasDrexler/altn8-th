package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup

import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell

internal class PopupContent(private val cells: List<AbstractCell>) {
    fun cells() = cells.toMutableList()

    fun onlyOneChoice() = numberOfFileCells() == 1

    fun onlyChoice(): FileCell {
        if (!onlyOneChoice()) {
            throw IllegalStateException("There are ${numberOfFileCells()} choices")
        }

        return fileCells().first()
    }

    fun correspondingCellsToElementAt(index: Int): Collection<Int> {
        if (isInvalidIndex(index)) {
            return listOf(index)
        }

        return when (cells[index]) {
            is CategoryCell -> findCorrespondingCells(index)
            is FileCell -> listOf(index)
            else -> listOf(index)
        }
    }

    private fun findCorrespondingCells(index: Int): Collection<Int> {
        val allPrecedingCells = cells.subList(0, index)
        val indexOfNextCategory = allPrecedingCells.indexOfLast { it is CategoryCell }
        val indexOfFirstCorrespondingCell = indexOfNextCategory + 1
        return ((indexOfFirstCorrespondingCell..index).toList())
    }

    private fun isValidIndex(index: Int): Boolean = index >= 0 && index < cells.size

    private fun isInvalidIndex(index: Int): Boolean = !isValidIndex(index)

    private fun numberOfFileCells() = fileCells().size

    private fun fileCells() = cells.filterIsInstance<FileCell>()
}