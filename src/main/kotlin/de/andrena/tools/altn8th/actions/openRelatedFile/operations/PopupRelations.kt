package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.PopupContent
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.CategoryCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class PopupRelations(private val prioritizedRelations: PrioritizedRelations, private val project: Project) {
    fun arrange(): PopupContent {
        val cells = prioritizedRelations.relations
            .map { it.relatedFile }
            .mapNotNull { FileConverter().toVirtualFile(it) }
            .mapNotNull { JetBrainsPsiFile().findFor(it, project) }
            .map { FileCell(it) }
        val fileCells: MutableList<AbstractCell> = mutableListOf()
        fileCells.addAll(cells)
        fileCells.add(CategoryCell("Category"))
        return PopupContent(fileCells)
    }
}
