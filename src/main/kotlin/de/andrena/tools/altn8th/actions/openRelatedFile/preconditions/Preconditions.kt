package de.andrena.tools.altn8th.actions.openRelatedFile.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

internal class Preconditions : Precondition {
    val preconditions = listOf(
        FileIsOpenedPrecondition()
    )

    override fun isNotFulfilled(actionEvent: AnActionEvent): Boolean =
        failingPreconditions(actionEvent).isEmpty()

    override fun handleFor(actionEvent: AnActionEvent) =
        failingPreconditions(actionEvent)
            .forEach { precondition -> precondition.handleFor(actionEvent) }

    private fun failingPreconditions(actionEvent: AnActionEvent) =
        preconditions
            .filter { precondition -> precondition.isNotFulfilled(actionEvent) }
}