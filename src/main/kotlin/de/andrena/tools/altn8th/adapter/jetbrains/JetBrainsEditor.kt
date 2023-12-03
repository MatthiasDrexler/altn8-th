package de.andrena.tools.altn8th.adapter.jetbrains

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys

internal class JetBrainsEditor {
    fun focusedFor(actionEvent: AnActionEvent) = actionEvent.getData(PlatformCoreDataKeys.EDITOR)
}