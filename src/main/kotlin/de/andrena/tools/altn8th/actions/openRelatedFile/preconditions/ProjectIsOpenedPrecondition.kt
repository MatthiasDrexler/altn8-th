package de.andrena.tools.altn8th.actions.openRelatedFile.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

internal class ProjectIsOpenedPrecondition : Precondition {
    override fun isNotFulfilled(actionEvent: AnActionEvent): Boolean =
        actionEvent.project !is Project

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}