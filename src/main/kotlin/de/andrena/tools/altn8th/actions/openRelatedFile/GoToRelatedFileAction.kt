package de.andrena.tools.altn8th.actions.openRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.ShowNoRelationsFoundHint
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.*
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.ProjectIsOpenedPrecondition
import de.andrena.tools.altn8th.adapter.File
import de.andrena.tools.altn8th.adapter.ProjectFiles
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy
import de.andrena.tools.altn8th.settings.SettingsPersistentStateComponent

class GoToRelatedFileAction : AnAction() {
    private val preconditions = listOf(
        ProjectIsOpenedPrecondition(),
        FileIsOpenedPrecondition(),
        EditorIsAvailablePrecondition()
    )

    private val relatedFilesStrategies = listOf(
        FindRelatedFilesByPostfixStrategy(),
        FindRelatedFilesByFileExtensionStrategy()
    )

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val preconditionsAreSatisfied = PreconditionsFor(actionEvent, preconditions).areSatisfied()
        if (!preconditionsAreSatisfied) {
            return
        }

        val project = checkNotNull(actionEvent.project) { "Project is a precondition" }
        val origin = checkNotNull(File().activeOn(actionEvent)) { "Active file as origin of action is a precondition" }

        val relations = RelatedFilesFrom(
            origin,
            ProjectFiles().allOf(project),
            SettingsPersistentStateComponent.getInstance().state,
            relatedFilesStrategies
        ).find()
        
        val relationsFound = AnyRelations(relations).areFound()
        if (!relationsFound) {
            ShowNoRelationsFoundHint(actionEvent).show()
            return
        }

        val selectedRelatedFile = RelatedFileToJumpTo(relations, project).select()
        if (selectedRelatedFile !is PsiFile) {
            return
        }

        SelectedRelatedFile(selectedRelatedFile).open()
    }
}
