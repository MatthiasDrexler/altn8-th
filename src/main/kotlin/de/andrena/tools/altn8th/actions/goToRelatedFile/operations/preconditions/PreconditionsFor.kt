package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent

class PreconditionsFor(
    private val actionEvent: AnActionEvent,
    private val preconditions: Collection<Precondition>
) {
    fun areSatisfied(): Boolean =
        findUnmetPreconditions(actionEvent).isEmpty()

    private fun findUnmetPreconditions(actionEvent: AnActionEvent) =
        preconditions.filter { it.isNotFulfilled(actionEvent) }
}
