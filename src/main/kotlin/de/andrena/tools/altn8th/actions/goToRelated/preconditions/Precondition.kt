package de.andrena.tools.altn8th.actions.goToRelated.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

interface Precondition {
    fun isNotFulfilled(actionEvent: AnActionEvent): Boolean
    fun handleFor(actionEvent: AnActionEvent)
}