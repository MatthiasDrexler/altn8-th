package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.components.JBTextArea
import java.awt.Dimension

fun JBTextArea.multilineLabel(): JBTextArea = apply {
    this.lineWrap = true
    this.wrapStyleWord = true
    this.isEditable = false
    this.isOpaque = false
    this.minimumSize = Dimension(0, this.preferredSize.height)
}
