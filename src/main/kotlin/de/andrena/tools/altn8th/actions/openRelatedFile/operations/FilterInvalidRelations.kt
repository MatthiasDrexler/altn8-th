package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation

internal class FilterInvalidRelations(
    private val prioritizedRelations: Collection<Relation>,
    private val project: Project
) {
    fun filter(): Collection<Relation> =
        prioritizedRelations.filter { isValidCandidate(it, project) }

    private fun isValidCandidate(relation: Relation, project: Project): Boolean =
        JetBrainsPsiFile().findFor(relation, project) is PsiFile
}
