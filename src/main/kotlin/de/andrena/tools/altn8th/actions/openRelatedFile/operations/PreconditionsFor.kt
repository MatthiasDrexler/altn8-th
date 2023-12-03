package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Precondition

internal class PreconditionsFor(
    private val actionEvent: AnActionEvent,
    private val preconditions: Collection<Precondition>
) {
    fun areSatisfied(): Boolean {
        val failingPreconditions = failingPreconditions(actionEvent)
        val allPreconditionsAreSatisfied = failingPreconditions.isEmpty()

        if (!allPreconditionsAreSatisfied) {
            handleUnsatisfiedPreconditionsFor(failingPreconditions)
        }

        return allPreconditionsAreSatisfied
    }

    private fun handleUnsatisfiedPreconditionsFor(failingPreconditions: List<Precondition>) {
        failingPreconditions.forEach { it.handleFor(actionEvent) }
    }

    private fun failingPreconditions(actionEvent: AnActionEvent) =
        preconditions.filter { it.notFulfilled(actionEvent) }
}