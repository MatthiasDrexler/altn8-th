package de.andrena.tools.altn8th.adapter

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.File

internal class Navigation(project: Project) {
    val openFile: (File) -> Unit = { file: File ->
        val virtualFile = FileConverter().toVirtualFile(file)
        virtualFile?.let {
            val psiFile = JetBrainsPsiFile().findFor(virtualFile, project)
            psiFile?.navigate(true)
        }
    }
}
