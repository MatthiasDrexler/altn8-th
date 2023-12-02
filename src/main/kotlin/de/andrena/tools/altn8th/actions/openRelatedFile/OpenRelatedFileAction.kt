package de.andrena.tools.altn8th.actions.openRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiManager
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Preconditions
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy
import de.andrena.tools.altn8th.settings.SettingsPersistentStateComponent
import java.nio.file.FileSystems

class OpenRelatedFileAction : AnAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val preconditions = Preconditions()
        if (preconditions.isNotFulfilled(actionEvent)) {
            preconditions.handleFor(actionEvent)
        }

        val origin = File.from(actionEvent.getData(PlatformCoreDataKeys.FILE_EDITOR)!!.file.path)
        val allFiles = FileEditorManager.getInstance(actionEvent.project!!)
            .allEditors.map { editor -> editor.file.path }
            .map { path -> File.from(path) }
        val settings = SettingsPersistentStateComponent.getInstance().state
        val relations = FindRelatedFilesByPostfixStrategy().findRelatedFiles(origin, allFiles, settings)

        val files = relations.relatedFiles
            .map { relatedFile -> relatedFile.path() }
            .map { path -> FileSystems.getDefault().getPath(path) }
            .map { path -> VirtualFileManager.getInstance().findFileByNioPath(path) }
            .map { PsiManager.getInstance(actionEvent.project!!).findFile(it!!) }

        if (files.isNotEmpty()) {
            files.first()?.navigate(true)
        }
    }
}
