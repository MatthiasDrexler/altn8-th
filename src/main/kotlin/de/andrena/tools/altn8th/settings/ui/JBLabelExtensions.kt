package de.andrena.tools.altn8th.settings.ui

import com.intellij.ui.components.JBLabel
import java.awt.Font

fun JBLabel.bold(): JBLabel {
    val currentFont = this.font
    this.font = currentFont.deriveFont(currentFont.style or Font.BOLD)
    return this
}
