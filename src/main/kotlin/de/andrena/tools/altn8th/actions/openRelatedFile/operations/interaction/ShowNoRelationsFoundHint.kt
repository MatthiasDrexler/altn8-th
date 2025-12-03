package de.andrena.tools.altn8th.actions.openRelatedFile.operations.interaction

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.adapter.interaction.InformationHint
import de.andrena.tools.altn8th.internationalization.I18n


internal class ShowNoRelationsFoundHint(private val actionEvent: AnActionEvent) {
    companion object {
        private val NO_RELATIONS_FOUND = I18n.lazyMessage("altn8.relations.empty")
    }

    fun show() = InformationHint().showFor(actionEvent, NO_RELATIONS_FOUND.get())
}
