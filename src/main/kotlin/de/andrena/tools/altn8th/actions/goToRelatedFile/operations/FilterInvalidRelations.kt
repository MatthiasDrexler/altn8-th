package de.andrena.tools.altn8th.actions.goToRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

class FilterInvalidRelations(private val project: Project) {

    fun filter(relationGroups: List<RelationGroup>): Collection<RelationGroup> =
        relationGroups.map { RelationGroup(it.category, it.relations.filter { isValidCandidate(it, project) }) }

    private fun isValidCandidate(relation: Relation, project: Project): Boolean =
        JetBrainsPsiFile().findFor(relation, project) is PsiFile
}
