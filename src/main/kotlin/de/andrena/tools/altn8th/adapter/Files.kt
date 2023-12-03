package de.andrena.tools.altn8th.adapter

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

internal class Files {
    fun findPsiFileIn(virtualFile: VirtualFile, project: Project): PsiFile? =
        PsiManager.getInstance(project).findFile(virtualFile)
}