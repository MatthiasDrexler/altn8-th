package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell

import com.intellij.ide.util.gotoByName.GotoFileCellRenderer
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
            is FileCell -> renderFileCell(list, value, index, isSelected, cellHasFocus)
            is CategoryCell -> renderCategoryCell(list, value, index, isSelected, cellHasFocus)
            else -> super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
        }
    }

    private fun renderFileCell(
        list: JList<out Any>?,
        value: FileCell,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component =
        gotoFileCellRenderer.getListCellRendererComponent(
            list,
            value.psiFile,
            index,
            isSelected,
            cellHasFocus
        )

    private fun renderCategoryCell(
        list: JList<out Any>?,
        value: CategoryCell,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component =
        super.getListCellRendererComponent(
            list,
            value.readableRepresentation(),
            index,
            isSelected,
            cellHasFocus
        )
}
