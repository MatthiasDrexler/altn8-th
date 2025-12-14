package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

interface Precondition {
    fun isNotFulfilled(actionEvent: AnActionEvent): Boolean
}
