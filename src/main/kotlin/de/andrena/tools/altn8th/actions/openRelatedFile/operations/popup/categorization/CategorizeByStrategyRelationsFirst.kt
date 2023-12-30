package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class CategorizeByStrategyRelationsFirst : CategorizationStrategy {
    override fun categorize(prioritizedRelations: PrioritizedRelations, project: Project): PopupContent {
        val popupCells: MutableList<AbstractCell> = mutableListOf()

        prioritizedRelations.relations.forEachIndexed(addFilesAndCategories(project, popupCells, prioritizedRelations))

        return PopupContent(popupCells)
    }

    private fun addFilesAndCategories(
        project: Project,
        popupCells: MutableList<AbstractCell>,
        prioritizedRelations: PrioritizedRelations
    ) =
        { index: Int, currentRelation: Relation ->
            run {
                JetBrainsPsiFile().findFor(currentRelation, project)
                    ?.let { popupCells.add(FileCell(it)) }

                if (isLastRelation(index, prioritizedRelations)
                    || nextRelationHasAnotherType(index, prioritizedRelations, currentRelation)
                ) {
                    addCategoryOfCurrentRelation(currentRelation, popupCells)
                }
            }
        }

    private fun nextRelationHasAnotherType(
        index: Int,
        prioritizedRelations: PrioritizedRelations,
        currentRelation: Relation
    ) =
        prioritizedRelations.relations[index + 1].type != currentRelation.type

    private fun addCategoryOfCurrentRelation(
        currentRelation: Relation,
        popupCells: MutableList<AbstractCell>
    ) {
        popupCells.add(CategoryCell(currentRelation.type.name()))
    }

    private fun isLastRelation(index: Int, prioritizedRelations: PrioritizedRelations) =
        index + 1 == prioritizedRelations.relations.size
}
