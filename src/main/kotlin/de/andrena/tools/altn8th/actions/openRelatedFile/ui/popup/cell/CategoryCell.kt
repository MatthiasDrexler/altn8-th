package de.andrena.tools.altn8th.actions.openRelatedFile.ui.popup.cell

internal data class CategoryCell(val title: String) : AbstractCell() {
    override fun readableRepresentation() = title
}
