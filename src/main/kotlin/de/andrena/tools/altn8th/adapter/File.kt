package de.andrena.tools.altn8th.adapter

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsFileEditor
import de.andrena.tools.altn8th.domain.File

internal class File {
    fun activeOn(actionEvent: AnActionEvent): File? {
        val activeEditor = JetBrainsFileEditor().activeFor(actionEvent)
        val openFile = activeEditor?.file
        val fileConverter = FileConverter()
        return openFile?.let { fileConverter.fromVirtualFile(it) }
    }

}