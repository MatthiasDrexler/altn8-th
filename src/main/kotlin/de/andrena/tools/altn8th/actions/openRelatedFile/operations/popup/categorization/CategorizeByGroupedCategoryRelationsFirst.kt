package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

internal class CategorizeByGroupedCategoryRelationsFirst : CategorizationStrategy {
    override fun categorize(relationGroups: List<RelationGroup>, project: Project): PopupContent {
        val popupCells = relationGroups
            .flatMap { relationGroup -> createCellsForRelationGroup(relationGroup, project) }

        return PopupContent(popupCells)
    }

    private fun createCellsForRelationGroup(relationGroup: RelationGroup, project: Project): List<AbstractCell> {
        val fileCells = relationGroup.relations
            .map { relation ->
                FileCell(relation, JetBrainsPsiFile().findFor(relation, project)!!)
            }
        return fileCells + listOf(CategoryCell("${relationGroup.category} ↲"))
    }
}
