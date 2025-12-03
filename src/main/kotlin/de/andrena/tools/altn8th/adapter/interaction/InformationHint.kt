package de.andrena.tools.altn8th.adapter.interaction

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsEditor

class InformationHint {
    fun showFor(actionEvent: AnActionEvent, message: String) {
        val editor = JetBrainsEditor().focusedFor(actionEvent)
        editor?.let { HintManager.getInstance().showInformationHint(it, message) }
    }
}
