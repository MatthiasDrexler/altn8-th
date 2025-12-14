package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.Precondition

class ProjectIsOpenedPrecondition : Precondition {
    override fun isNotFulfilled(actionEvent: AnActionEvent) =
        actionEvent.project !is Project
}
