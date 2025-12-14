package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.categorization.PopupContentConverter
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

class PopupRelations(
    private val popupContentConverter: PopupContentConverter,
    private val project: Project
) {
    fun arrange(relationGroups: List<RelationGroup>): PopupContent =
        popupContentConverter.convert(relationGroups, project)
}
