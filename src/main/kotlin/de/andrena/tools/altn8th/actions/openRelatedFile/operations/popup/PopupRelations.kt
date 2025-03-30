package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization.CategorizationStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

internal class PopupRelations(
    private val prioritizedRelations: List<RelationGroup>,
    private val project: Project,
    private val categorizationStrategy: CategorizationStrategy
) {
    fun arrange(): PopupContent = categorizationStrategy.categorize(prioritizedRelations, project)
}
