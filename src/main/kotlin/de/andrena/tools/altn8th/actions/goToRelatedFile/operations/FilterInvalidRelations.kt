package de.andrena.tools.altn8th.actions.goToRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation

class FilterInvalidRelations(private val project: Project) {

    fun filter(prioritizedRelations: Collection<Relation>): Collection<Relation> =
        prioritizedRelations.filter { isValidCandidate(it, project) }

    private fun isValidCandidate(relation: Relation, project: Project): Boolean =
        JetBrainsPsiFile().findFor(relation, project) is PsiFile
}
