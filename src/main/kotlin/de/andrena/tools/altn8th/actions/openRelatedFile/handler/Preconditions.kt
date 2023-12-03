package de.andrena.tools.altn8th.actions.openRelatedFile.handler

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Precondition

internal class Preconditions(
    private val actionEvent: AnActionEvent,
    private val preconditions: Collection<Precondition>
) {
    fun check() {
        if (notFulfilled(actionEvent)) {
            handleFor(actionEvent)
        }
    }

    private fun notFulfilled(actionEvent: AnActionEvent) =
        failingPreconditions(actionEvent).isEmpty()

    private fun handleFor(actionEvent: AnActionEvent) =
        failingPreconditions(actionEvent)
            .forEach { precondition -> precondition.handleFor(actionEvent) }

    private fun failingPreconditions(actionEvent: AnActionEvent) =
        preconditions
            .filter { precondition -> precondition.notFulfilled(actionEvent) }
}