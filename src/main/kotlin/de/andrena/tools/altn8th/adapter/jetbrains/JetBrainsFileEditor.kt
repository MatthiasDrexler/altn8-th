package de.andrena.tools.altn8th.adapter.jetbrains

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys

class JetBrainsFileEditor {
    fun activeFor(actionEvent: AnActionEvent) = actionEvent.getData(PlatformCoreDataKeys.FILE_EDITOR)
}