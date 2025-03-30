package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization.PopupContentConverter
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup

internal class PopupRelations(
    private val relationGroups: List<RelationGroup>,
    private val popupContentConverter: PopupContentConverter,
    private val project: Project
) {
    fun arrange(): PopupContent = popupContentConverter.convert(relationGroups, project)
}
