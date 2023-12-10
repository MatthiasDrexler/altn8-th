package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class RelatedFileToJumpTo(
    private val prioritizedRelations: PrioritizedRelations,
    private val project: Project
) {
    fun select(): PsiFile? {
        val psiFiles = prioritizedRelations.relations
            .map { it.relatedFile }
            .mapNotNull { FileConverter().toVirtualFile(it) }
            .map { JetBrainsPsiFile().findFor(it, project) }

        return psiFiles.first()
    }
}
