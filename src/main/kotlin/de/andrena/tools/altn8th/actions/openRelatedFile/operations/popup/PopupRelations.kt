package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization.CategorizationStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.PrioritizedRelations

internal class PopupRelations(
    private val prioritizedRelations: PrioritizedRelations,
    private val project: Project,
    private val categorizationStrategy: CategorizationStrategy
) {
    fun arrange(): PopupContent = categorizationStrategy.categorize(prioritizedRelations, project)
}
