package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup

import javax.swing.DefaultListSelectionModel

internal class RelatedFilesSelectionModel : DefaultListSelectionModel() {
    override fun setSelectionInterval(index0: Int, index1: Int) {
        super.setSelectionInterval(index0, index1)
        if (index0 == index1) {
        }
        if (index0 != index1) {
        }
    }

    override fun addSelectionInterval(index0: Int, index1: Int) {
        super.addSelectionInterval(index0, index1)
        if (index0 == index1) {
        }
        if (index0 != index1) {
        }
    }
}
