package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

class SatisfiedPrecondition: Precondition {
    override fun isNotFulfilled(actionEvent: AnActionEvent): Boolean = false
}
