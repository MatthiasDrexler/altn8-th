package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.JBUI
import java.awt.Dimension

fun JBTextArea.multilineLabel(): JBTextArea = apply {
    this.lineWrap = true
    this.wrapStyleWord = true
    this.isEditable = false
    this.isOpaque = false
    this.font = JBUI.Fonts.label()
    this.minimumSize = Dimension(0, this.preferredSize.height)
}
