package de.andrena.tools.altn8th.actions.goToRelatedFile

import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.*
import de.andrena.tools.altn8th.domain.File
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
        val relations = RelatedFilesWithin(relatedFilesStrategies, settings).findFor(origin, allFiles)
        if (relations.isEmpty()) {
            return emptyList()
        }

        val deduplicatedRelations = DeduplicateRelations(deduplicationStrategy).deduplicate(relations)
        val groupedRelations = GroupRelations(groupStrategy).group(deduplicatedRelations)
        val orderedRelationGroups = OrderRelationGroups(relationOrderStrategy, relationGroupOrderStrategy)
            .arrange(groupedRelations)

        return orderedRelationGroups
    }
}
