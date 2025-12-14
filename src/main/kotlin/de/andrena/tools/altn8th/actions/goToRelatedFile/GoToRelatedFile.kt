package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.*
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.interaction.ShowNoRelationsFoundHint
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.PopupRelations
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.ShowRelatedFiles
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.categorization.PopupContentConverter
import de.andrena.tools.altn8th.adapter.ProjectFiles
import de.andrena.tools.altn8th.domain.File
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
    private val actionEvent: AnActionEvent,
    private val editor: Editor,
    private val project: Project,
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

    fun from(origin: File) {
        val relations = RelatedFilesWithin(relatedFilesStrategies, settings)
            .findFor(origin, ProjectFiles(project).all())

        if (relations.isEmpty()) {
            ShowNoRelationsFoundHint(actionEvent).show()
            return
        }

        val validRelations = FilterInvalidRelations(project).filter(relations)
        val deduplicatedRelations = DeduplicateRelations(deduplicationStrategy).deduplicate(validRelations)
        val groupedRelations = GroupRelations(groupStrategy).group(deduplicatedRelations)
        val orderedRelationGroups = OrderRelationGroups(relationOrderStrategy, relationGroupOrderStrategy)
            .arrange(groupedRelations)

        val relationsForPopup = PopupRelations(PopupContentConverter(), project).arrange(orderedRelationGroups)
        if (relationsForPopup.hasOnlyOneChoice()) {
            NavigateTo(relationsForPopup.firstChoice).directly()
            return
        }

        ShowRelatedFiles(editor).popUp(relationsForPopup)
    }
}
