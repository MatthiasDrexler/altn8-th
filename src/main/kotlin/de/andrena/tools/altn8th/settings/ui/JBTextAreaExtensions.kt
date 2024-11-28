package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Dimension

fun JBTextArea.multilineContextHelp(): JBTextArea = apply {
    this.lineWrap = true
    this.wrapStyleWord = true
    this.isOpaque = false
    this.border = null
    this.font = JBUI.Fonts.label()
    this.isEnabled = false
    this.disabledTextColor = UIUtil.getContextHelpForeground()
    this.minimumSize = Dimension(0, this.preferredSize.height)
}
