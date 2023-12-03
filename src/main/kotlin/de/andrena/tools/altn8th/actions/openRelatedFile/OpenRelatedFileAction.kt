package de.andrena.tools.altn8th.actions.openRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Preconditions
import de.andrena.tools.altn8th.adapter.Editor
import de.andrena.tools.altn8th.adapter.Files
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.project.ProjectFiles
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy
import de.andrena.tools.altn8th.settings.SettingsPersistentStateComponent

class OpenRelatedFileAction : AnAction() {
    private val relatedFilesStrategies = listOf(
        FindRelatedFilesByPostfixStrategy(),
        FindRelatedFilesByFileExtensionStrategy()
    )

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val preconditions = Preconditions()
        if (preconditions.notFulfilled(actionEvent)) {
            preconditions.handleFor(actionEvent)
        }

        val project = checkNotNull(actionEvent.project) { "Project must be set" }
        val origin = checkNotNull(Editor().activeFileOn(actionEvent))

        val allFiles = ProjectFiles().allOf(project)
        val settings = SettingsPersistentStateComponent.getInstance().state
        val relations = relatedFilesStrategies.map { it.find(origin, allFiles, settings) }

        val files = relations.flatMap { it.relatedFiles }
            .mapNotNull { FileConverter().toVirtualFile(it) }
            .map { Files().findPsiFileIn(it, project) }

        if (files.isNotEmpty()) {
            files.first()?.navigate(true)
        }
    }
}
