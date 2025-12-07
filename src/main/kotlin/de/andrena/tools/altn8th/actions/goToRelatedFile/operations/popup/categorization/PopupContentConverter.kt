package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.categorization

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell.AbstractCell
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.visualization.cell.FileCell
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

class PopupContentConverter {
    fun convert(relationGroups: List<RelationGroup>, project: Project): PopupContent {
        val popupCells = relationGroups
            .flatMap { relationGroup -> createCellsForRelationGroup(relationGroup, project) }

        return PopupContent(popupCells)
    }

    private fun createCellsForRelationGroup(relationGroup: RelationGroup, project: Project): List<AbstractCell> {
        val fileCells = relationGroup.relations
            .mapNotNull { relation ->
                JetBrainsPsiFile().findFor(relation, project)?.let {
                    FileCell(relation, it)
                }
            }
        val categoryCell = listOf(CategoryCell("${relationGroup.category} ↲"))
        return fileCells + categoryCell
    }
}
