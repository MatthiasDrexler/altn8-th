package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class CategorizeByDescription : CategorizationStrategy {
    override fun categorize(prioritizedRelations: PrioritizedRelations, project: Project): PopupContent {
        val popupCells: MutableList<AbstractCell> = mutableListOf()

        prioritizedRelations.relations.forEachIndexed { index, currentRelation ->
            run {
                JetBrainsPsiFile().findFor(currentRelation, project)
                    ?.let { popupCells.add(FileCell(it)) }

                if (index + 1 < prioritizedRelations.relations.size) {
                    val followingRelation = prioritizedRelations.relations[index + 1]
                    if (followingRelation.type != currentRelation.type) {
                        popupCells.add(CategoryCell(currentRelation.type.toString()))
                    }
                } else {
                    popupCells.add(CategoryCell(currentRelation.type.toString()))
                }
            }
        }

        return PopupContent(popupCells)
    }
}
