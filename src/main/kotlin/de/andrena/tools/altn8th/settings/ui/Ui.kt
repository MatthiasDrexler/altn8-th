package de.andrena.tools.altn8th.settings.ui

import javax.swing.JPanel

interface Ui {
    val panel: JPanel

    fun isModified(): Boolean
    fun apply()
    fun reset()
}
