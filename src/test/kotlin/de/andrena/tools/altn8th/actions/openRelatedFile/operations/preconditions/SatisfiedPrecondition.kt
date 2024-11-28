package de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

class SatisfiedPrecondition: Precondition {
    override fun notFulfilled(actionEvent: AnActionEvent): Boolean = false

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}
