package de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Precondition

class UnsatisfiedPrecondition: Precondition {
    override fun notFulfilled(actionEvent: AnActionEvent): Boolean = true

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}