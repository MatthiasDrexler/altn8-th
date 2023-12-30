package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell

internal abstract class AbstractCell {
    abstract fun cellText(): String
    abstract fun tooltipText(): String

    override fun toString() = cellText()
}
