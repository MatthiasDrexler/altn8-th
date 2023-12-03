package de.andrena.tools.altn8th.adapter

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import de.andrena.tools.altn8th.domain.File

internal class Editor {
    fun activeFileOn(actionEvent: AnActionEvent): File? {
        val activeEditor = activeEditorFor(actionEvent)
        val openFile = activeEditor?.file
        val pathOfOpenFile = openFile?.path

        return pathOfOpenFile?.let { File.from(it) }
    }

    private fun activeEditorFor(actionEvent: AnActionEvent) = actionEvent.getData(PlatformCoreDataKeys.FILE_EDITOR)
}