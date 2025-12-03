package de.andrena.tools.altn8th.adapter.jetbrains

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.domain.relatedFiles.Relation

class JetBrainsPsiFile {
    fun findFor(virtualFile: VirtualFile, project: Project): PsiFile? =
        PsiManager.getInstance(project).findFile(virtualFile)

    fun findFor(relation: Relation, project: Project): PsiFile? =
        FileConverter().toVirtualFile(relation.relatedFile)
            ?.let { JetBrainsPsiFile().findFor(it, project) }
}
