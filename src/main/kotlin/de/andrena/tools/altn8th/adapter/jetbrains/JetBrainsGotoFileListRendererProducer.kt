package de.andrena.tools.altn8th.adapter.jetbrains

import com.intellij.ide.util.gotoByName.GotoFileCellRenderer
import javax.swing.ListCellRenderer

internal class JetBrainsGotoFileListRendererProducer {
    fun gotoFileCellRenderer(width: Int): (ListCellRenderer<*>) -> ListCellRenderer<*> = {
        GotoFileCellRenderer(width)
    }
}
