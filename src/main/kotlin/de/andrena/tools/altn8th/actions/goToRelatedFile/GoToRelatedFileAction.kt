package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.PreconditionsFor
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.ProjectIsOpenedPrecondition
import de.andrena.tools.altn8th.adapter.File
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

        GoToRelatedFile(actionEvent, editor, project, settings).from(origin)
    }

    private val settings get() = SettingsPersistentStateComponent.getInstance().state
}
