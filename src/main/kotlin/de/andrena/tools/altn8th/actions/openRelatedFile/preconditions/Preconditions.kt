package de.andrena.tools.altn8th.actions.openRelatedFile.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.ProjectIsOpenedPrecondition

internal class Preconditions : Precondition {
    private val preconditions = listOf(
        ProjectIsOpenedPrecondition(),
        FileIsOpenedPrecondition(),
        EditorIsAvailablePrecondition()
    )

    override fun notFulfilled(actionEvent: AnActionEvent) =
        failingPreconditions(actionEvent).isEmpty()

    override fun handleFor(actionEvent: AnActionEvent) =
        failingPreconditions(actionEvent)
            .forEach { precondition -> precondition.handleFor(actionEvent) }

    private fun failingPreconditions(actionEvent: AnActionEvent) =
        preconditions
            .filter { precondition -> precondition.notFulfilled(actionEvent) }
}