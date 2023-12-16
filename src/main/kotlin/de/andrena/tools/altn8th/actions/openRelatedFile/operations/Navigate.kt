package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell

internal class Navigate(private val cell: FileCell) {
    fun directly() {
        cell.psiFile.navigate(true)
    }
}
