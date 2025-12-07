package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

class PreconditionsFor(
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
