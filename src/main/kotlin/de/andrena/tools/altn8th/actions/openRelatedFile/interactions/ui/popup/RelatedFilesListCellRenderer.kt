package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup

import com.intellij.ide.util.gotoByName.GotoFileCellRenderer
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ui.popup.cell.FileCell
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JList

internal class RelatedFilesListCellRenderer(maximumWidth: Int) : DefaultListCellRenderer() {
    private val gotoFileCellRenderer = GotoFileCellRenderer(maximumWidth)

    override fun getListCellRendererComponent(
        list: JList<out Any>?,
        value: Any?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        return when (value) {
            is FileCell -> gotoFileCellRenderer.getListCellRendererComponent(
                list,
                value.psiFile,
                index,
                isSelected,
                cellHasFocus
            )

            is CategoryCell -> super.getListCellRendererComponent(
                list,
                value.readableRepresentation(),
                index,
                isSelected,
                cellHasFocus
            )

            else -> super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
        }
    }
}
