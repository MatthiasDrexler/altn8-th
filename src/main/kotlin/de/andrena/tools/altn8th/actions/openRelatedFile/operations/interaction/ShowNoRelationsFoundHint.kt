package de.andrena.tools.altn8th.actions.openRelatedFile.operations.interaction

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.adapter.interaction.InformationHint

internal class ShowNoRelationsFoundHint(private val actionEvent: AnActionEvent) {
    fun show() = InformationHint().showFor(actionEvent, "No corresponding file(s) found")
}
