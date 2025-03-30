package de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups

import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.settings.SettingsState


internal class SortRelationGroupsByOrderOfCategoriesInSettings(state: SettingsState) : SortRelationGroupsStrategy {
    override fun sort(relationGroups: Collection<RelationGroup>): List<RelationGroup> {
        return relationGroups.toList()
    }
}
