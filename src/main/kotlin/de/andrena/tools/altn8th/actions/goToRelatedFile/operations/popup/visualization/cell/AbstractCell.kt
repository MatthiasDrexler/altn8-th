package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell

abstract class AbstractCell {
    abstract fun cellText(): String
    abstract fun tooltipText(): String

    override fun toString() = cellText()
}
