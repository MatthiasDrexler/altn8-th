package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell

internal data class CategoryCell(val title: String) : AbstractCell() {
    override fun readableRepresentation() = title
}
