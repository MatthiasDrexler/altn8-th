package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell

internal class NavigateTo(private val cell: FileCell) {
    fun directly() {
        cell.psiFile.navigate(true)
    }
}
