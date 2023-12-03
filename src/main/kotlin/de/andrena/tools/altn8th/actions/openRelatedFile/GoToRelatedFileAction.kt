package de.andrena.tools.altn8th.actions.openRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.actions.openRelatedFile.handler.FindRelatedFiles
import de.andrena.tools.altn8th.actions.openRelatedFile.handler.OpenRelatedFile
import de.andrena.tools.altn8th.actions.openRelatedFile.handler.Preconditions
import de.andrena.tools.altn8th.actions.openRelatedFile.handler.SelectRelatedFile
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations.ProjectIsOpenedPrecondition
import de.andrena.tools.altn8th.adapter.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy

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
        Preconditions(actionEvent, preconditions).check()

        val project = checkNotNull(actionEvent.project) { "Project must be set" }
        val origin = checkNotNull(File().activeOn(actionEvent))

        val relations = FindRelatedFiles(project, origin, relatedFilesStrategies).find()
        
        val selectedRelatedFile = SelectRelatedFile(actionEvent, relations, project).select()
        if (selectedRelatedFile !is PsiFile) {
            return
        }

        OpenRelatedFile(selectedRelatedFile).open()
    }
}
