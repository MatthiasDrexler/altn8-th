package de.andrena.tools.altn8th.actions.goToRelatedFile

import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.DeduplicateRelations
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.GroupRelations
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.OrderRelationGroups
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.RelatedFilesWithin
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies.DeduplicateRelationsByTakingFirstOccurrence
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filePath.FindRelatedFilesByFilePathRegexStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex.FindRelatedFilesByFilenameRegexStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix.FindRelatedFilesByPrefixStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.group.strategies.GroupByCategoryStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups.SortRelationGroupsByOrderOfCategoriesInSettings
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.relations.SortRelationsByFlattening
import de.andrena.tools.altn8th.domain.settings.SettingsState

class GoToRelatedFile(
    private val settings: SettingsState
) {
    private val relatedFilesStrategies = listOf(
        FindRelatedFilesByPrefixStrategy(),
        FindRelatedFilesByPostfixStrategy(),
        FindRelatedFilesByFilenameRegexStrategy(),
        FindRelatedFilesByFileExtensionStrategy(),
        FindRelatedFilesByFilePathRegexStrategy(),
    )

    private val deduplicationStrategy = DeduplicateRelationsByTakingFirstOccurrence()
    private val groupStrategy = GroupByCategoryStrategy()
    private val relationGroupOrderStrategy = SortRelationGroupsByOrderOfCategoriesInSettings(settings)
    private val relationOrderStrategy = SortRelationsByFlattening()

    fun from(origin: File, allFiles: List<File>): List<RelationGroup> {
        val relations = findRelations(origin, allFiles)
        if (relations.isEmpty()) {
            return emptyList()
        }

        val deduplicatedRelations = deduplicateRelations(relations)
        val groupedRelations = groupRelations(deduplicatedRelations)
        val orderedRelationGroups = orderRelationGroups(groupedRelations)

        return orderedRelationGroups
    }

    private fun findRelations(
        origin: File,
        allFiles: List<File>
    ): Collection<Relation> = RelatedFilesWithin(relatedFilesStrategies, settings).findFor(origin, allFiles)

    private fun deduplicateRelations(relations: Collection<Relation>): Collection<Relation> =
        DeduplicateRelations(deduplicationStrategy).deduplicate(relations)

    private fun groupRelations(deduplicatedRelations: Collection<Relation>): Collection<RelationGroup> =
        GroupRelations(groupStrategy).group(deduplicatedRelations)

    private fun orderRelationGroups(groupedRelations: Collection<RelationGroup>): List<RelationGroup> =
        OrderRelationGroups(relationOrderStrategy, relationGroupOrderStrategy)
            .arrange(groupedRelations)
}
