package de.andrena.tools.altn8th.actions.goToRelated

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiManager
import de.andrena.tools.altn8th.actions.goToRelated.preconditions.Preconditions
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesByNamePostfixStrategy
import de.andrena.tools.altn8th.settings.AltN8Settings
import java.nio.file.FileSystems

class GoToRelatedFileAction : AnAction() {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        val preconditions = Preconditions()
        if (preconditions.isNotFulfilled(actionEvent)) {
            preconditions.handleFor(actionEvent)
        }

        val origin = File.from(actionEvent.getData(PlatformCoreDataKeys.FILE_EDITOR)!!.file.path)
        val allFiles = FileEditorManager.getInstance(actionEvent.project!!)
            .allEditors.map { editor -> editor.file.path }
            .map { path -> File.from(path) }
        val settings = AltN8Settings.getInstance().state
        val relatedFiles = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(origin, allFiles, settings)

        val files = relatedFiles
            .map { relatedFile -> relatedFile.file.path() }
            .map { path -> FileSystems.getDefault().getPath(path) }
            .map { path -> VirtualFileManager.getInstance().findFileByNioPath(path) }
            .map { PsiManager.getInstance(actionEvent.project!!).findFile(it!!) }

        if (files.isNotEmpty()) {
            files.last()?.navigate(true)
        }
    }
}
