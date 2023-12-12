package de.andrena.tools.altn8th.actions.openRelatedFile.ui

import com.intellij.ide.util.gotoByName.GotoFileCellRenderer
import com.intellij.psi.PsiElement
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
        if (value is PsiElement) {
            return gotoFileCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
        }

        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
    }
}
