package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell

data class CategoryCell(val title: String) : AbstractCell() {
    override fun cellText() = title

    override fun tooltipText() = title
}
