package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization

import com.intellij.ide.util.gotoByName.GotoFileCellRenderer
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell
import java.awt.Component
import java.awt.Font
import javax.swing.DefaultListCellRenderer
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.SwingConstants

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
    ): Component {
        val categoryComponent = super.getListCellRendererComponent(
            list,
            value.cellText(),
            index,
            isSelected,
            cellHasFocus
        )

        categoryComponent.font = categoryComponent.font.deriveFont(Font.BOLD)
        if (categoryComponent is JLabel) {
            categoryComponent.horizontalAlignment = SwingConstants.RIGHT
        }

        return categoryComponent
    }
}
