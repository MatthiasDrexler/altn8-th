package de.andrena.tools.altn8th.adapter.jetbrains

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

internal class JetBrainsPsiFile {
    fun findFor(virtualFile: VirtualFile, project: Project) =
        PsiManager.getInstance(project).findFile(virtualFile)
}