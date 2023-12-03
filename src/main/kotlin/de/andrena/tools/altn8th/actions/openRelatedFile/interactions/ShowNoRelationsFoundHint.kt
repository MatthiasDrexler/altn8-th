package de.andrena.tools.altn8th.actions.openRelatedFile.interactions

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.adapter.interaction.InlineHint

internal class ShowNoRelationsFoundHint(private val actionEvent: AnActionEvent) {
    fun show() = InlineHint().showFor(actionEvent, "No corresponding file(s) found")
}
