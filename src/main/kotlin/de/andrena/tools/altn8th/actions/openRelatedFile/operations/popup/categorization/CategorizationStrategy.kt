package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupContent
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

internal interface CategorizationStrategy {
    fun categorize(relationGroups: List<RelationGroup>, project: Project): PopupContent
}
