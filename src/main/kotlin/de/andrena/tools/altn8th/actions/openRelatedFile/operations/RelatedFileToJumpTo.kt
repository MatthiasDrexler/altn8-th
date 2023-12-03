package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType

internal class RelatedFileToJumpTo(
    private val relations: Collection<RelationsByType>,
    private val project: Project
) {
    fun select(): PsiFile? {
        val psiFiles = relations.flatMap { it.relatedFiles }
            .mapNotNull { FileConverter().toVirtualFile(it) }
            .map { JetBrainsPsiFile().findFor(it, project) }

        return psiFiles.first()
    }
}
