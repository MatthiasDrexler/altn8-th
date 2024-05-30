package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class CategorizeByGroupedCategoryRelationsFirst : CategorizationStrategy {
    override fun categorize(prioritizedRelations: PrioritizedRelations, project: Project): PopupContent {
        val popupCells = prioritizedRelations.relations
            .map { Pair(it.type.category(), FileCell(it, JetBrainsPsiFile().findFor(it, project)!!)) }
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second })
            .flatMap { it.value + listOf(CategoryCell("${it.key} ↲")) }

        return PopupContent(popupCells)
    }
}
