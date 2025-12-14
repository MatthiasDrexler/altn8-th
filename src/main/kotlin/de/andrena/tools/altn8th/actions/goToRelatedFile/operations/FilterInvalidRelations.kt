package de.andrena.tools.altn8th.actions.goToRelatedFile.operations

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

class FilterInvalidRelations(private val project: Project) {

    fun filter(relationGroups: List<RelationGroup>): List<RelationGroup> =
        relationGroups.map { relationGroup ->
            RelationGroup(
                relationGroup.category,
                relationGroup.relations
                    .filter { doesNotRelateToItself(it) }
                    .filter { canBeOpened(it, project) })
        }

    private fun doesNotRelateToItself(relation: Relation): Boolean =
        relation.relatedFile != relation.origin

    private fun canBeOpened(relation: Relation, project: Project): Boolean =
        JetBrainsPsiFile().findFor(relation, project) is PsiFile
}
