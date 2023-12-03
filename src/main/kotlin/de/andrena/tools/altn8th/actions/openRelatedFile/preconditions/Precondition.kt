package de.andrena.tools.altn8th.actions.openRelatedFile.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

interface Precondition {
    fun notFulfilled(actionEvent: AnActionEvent): Boolean
    fun handleFor(actionEvent: AnActionEvent)
}