package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.FilterInvalidRelations
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.NavigateTo
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.interaction.ShowNoRelationsFoundHint
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.PopupRelations
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.ShowRelatedFiles
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.categorization.PopupContentConverter
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.PreconditionsFor
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.ProjectIsOpenedPrecondition
import de.andrena.tools.altn8th.adapter.File
import de.andrena.tools.altn8th.adapter.ProjectFiles
import de.andrena.tools.altn8th.domain.relatedFiles.GoToRelatedFile
import de.andrena.tools.altn8th.settings.SettingsPersistentStateComponent

class GoToRelatedFileAction : AnAction() {

    private val preconditions = listOf(
        ProjectIsOpenedPrecondition(),
        FileIsOpenedPrecondition(),
        EditorIsAvailablePrecondition()
    )

    override fun actionPerformed(actionEvent: AnActionEvent) {
        if (!PreconditionsFor(actionEvent, preconditions).areSatisfied()) {
            return
        }

        val project =
            checkNotNull(actionEvent.project) { "Project must be set to find related files" }
        val editor =
            checkNotNull(FileEditorManager.getInstance(project).selectedTextEditor) { "Editor must be set to open related file in" }
        val origin =
            checkNotNull(File().activeOn(actionEvent)) { "Active file as origin of action must be set to determine related files" }

        val orderedRelationGroups = GoToRelatedFile(settings).from(origin, ProjectFiles(project).all())
        if (orderedRelationGroups.isEmpty()) {
            ShowNoRelationsFoundHint(actionEvent).show()
            return
        }

        val relationGroupWithOnlyValidRelations = FilterInvalidRelations(project).filter(orderedRelationGroups)

        val relationsForPopup = PopupRelations(PopupContentConverter(), project).arrange(orderedRelationGroups)
        if (relationsForPopup.hasOnlyOneChoice()) {
            NavigateTo(relationsForPopup.firstChoice).directly()
            return
        }

        ShowRelatedFiles(editor).popUp(relationsForPopup)
    }

    private val settings get() = SettingsPersistentStateComponent.getInstance().state
}
