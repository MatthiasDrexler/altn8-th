package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.*
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.interaction.ShowNoRelationsFoundHint
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.PopupRelations
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.ShowRelatedFiles
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.categorization.PopupContentConverter
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.PreconditionsFor
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations.ProjectIsOpenedPrecondition
import de.andrena.tools.altn8th.adapter.File
import de.andrena.tools.altn8th.adapter.ProjectFiles
import de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies.DeduplicateRelationsByTakingFirstOccurrence
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filePath.FindRelatedFilesByFilePathRegexStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex.FindRelatedFilesByFilenameRegexStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix.FindRelatedFilesByPostfixStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix.FindRelatedFilesByPrefixStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.group.strategies.GroupByCategoryStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.groups.SortRelationGroupsByOrderOfCategoriesInSettings
import de.andrena.tools.altn8th.domain.relatedFiles.order.strategies.relations.SortRelationsByFlattening
import de.andrena.tools.altn8th.settings.SettingsPersistentStateComponent

class GoToRelatedFileAction : AnAction() {
    private val preconditions = listOf(
        ProjectIsOpenedPrecondition(),
        FileIsOpenedPrecondition(),
        EditorIsAvailablePrecondition()
    )

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

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val preconditionsAreSatisfied = PreconditionsFor(actionEvent, preconditions).areSatisfied()
        if (!preconditionsAreSatisfied) {
            return
        }

        val project = checkNotNull(actionEvent.project) { "Project must be set to find related files" }
        val editor =
            checkNotNull(FileEditorManager.getInstance(project).selectedTextEditor) { "Editor must be set to open related file in" }
        val origin =
            checkNotNull(File().activeOn(actionEvent)) { "Active file as origin of action must be set to determine related files" }

        val relations = RelatedFilesWithin(relatedFilesStrategies, settings).findFor(
            origin,
            ProjectFiles(project).all()
        )

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

    private val settings get() = SettingsPersistentStateComponent.getInstance().state
}
