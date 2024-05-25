package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell

internal class PopupContent(private val cells: List<AbstractCell>) {
    fun cells() = cells.toMutableList()

    fun hasOnlyOneChoice() = numberOfFileCells == 1

    val firstChoice
        get(): FileCell {
            if (numberOfFileCells < 1) {
                throw IllegalStateException("No choices")
            }

            return findFileCells().first()
        }

    fun cellAt(index: Int): AbstractCell? {
        if (isInvalidIndex(index)) {
            return null
        }

        return cells[index]
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

    private val numberOfFileCells get() = findFileCells().size

    private fun findCorrespondingCells(index: Int): Collection<Int> {
        val allPrecedingCells = cells.subList(0, index)
        val indexOfNextCategory = allPrecedingCells.indexOfLast { it is CategoryCell }
        val indexOfFirstCorrespondingCell = indexOfNextCategory + 1
        return ((indexOfFirstCorrespondingCell..index).toList())
    }

    private fun isValidIndex(index: Int): Boolean = index >= 0 && index < cells.size

    private fun isInvalidIndex(index: Int): Boolean = !isValidIndex(index)

    private fun findFileCells() = cells.filterIsInstance<FileCell>()
}
