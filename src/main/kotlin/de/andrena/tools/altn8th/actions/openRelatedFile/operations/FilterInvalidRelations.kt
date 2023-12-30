package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class FilterInvalidRelations(
    private val prioritizedRelations: PrioritizedRelations,
    private val project: Project
) {
    fun filter(): PrioritizedRelations {
        val filteredRelations = prioritizedRelations.relations.filter { isValidCandidate(it, project) }

        return PrioritizedRelations(filteredRelations)
    }

    private fun isValidCandidate(relation: Relation, project: Project): Boolean =
        JetBrainsPsiFile().findFor(relation, project) is PsiFile
}
