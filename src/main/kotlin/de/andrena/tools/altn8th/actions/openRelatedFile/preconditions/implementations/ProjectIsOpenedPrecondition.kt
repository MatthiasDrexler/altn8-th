package de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Precondition

internal class ProjectIsOpenedPrecondition : Precondition {
    override fun notFulfilled(actionEvent: AnActionEvent) =
        actionEvent.project !is Project

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}