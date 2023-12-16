package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell

internal abstract class AbstractCell {
    abstract fun readableRepresentation(): String

    override fun toString() = readableRepresentation()
}
