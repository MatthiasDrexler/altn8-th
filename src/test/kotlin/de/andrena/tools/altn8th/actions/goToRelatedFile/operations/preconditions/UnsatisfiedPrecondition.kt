package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

class UnsatisfiedPrecondition: Precondition {
    override fun notFulfilled(actionEvent: AnActionEvent): Boolean = true

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}
