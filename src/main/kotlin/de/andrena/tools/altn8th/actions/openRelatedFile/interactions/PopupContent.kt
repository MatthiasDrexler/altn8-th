package de.andrena.tools.altn8th.actions.openRelatedFile.interactions

import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.FileCell

internal class PopupContent(private val cells: Collection<AbstractCell>) {
    fun onlyOneChoice() = numberOfFileCells() == 1

    fun onlyChoice(): FileCell {
        if (!onlyOneChoice()) {
            throw IllegalStateException("There are ${numberOfFileCells()} choices")
        }

        return fileCells().first()
    }

    fun cells() = cells.toMutableList()

    private fun numberOfFileCells() = fileCells().size

    private fun fileCells() = cells.filterIsInstance<FileCell>()
}
