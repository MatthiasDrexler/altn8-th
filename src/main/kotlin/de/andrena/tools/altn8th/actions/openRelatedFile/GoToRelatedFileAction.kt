package de.andrena.tools.altn8th.actions.openRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.*
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.interaction.ShowNoRelationsFoundHint
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupRelations
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.ShowRelatedFiles
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization.PopupContentConverter
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.PreconditionsFor
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.implementations.EditorIsAvailablePrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.implementations.FileIsOpenedPrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.implementations.ProjectIsOpenedPrecondition
import de.andrena.tools.altn8th.adapter.File
import de.andrena.tools.altn8th.adapter.ProjectFiles
import de.andrena.tools.altn8th.domain.relatedFiles.deduplicate.strategies.DeduplicateRelationsByTakingFirstOccurrence
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.fileExtension.FindRelatedFilesByFileExtensionStrategy
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.freeRegex.FindRelatedFilesByFreeRegexStrategy
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
        FindRelatedFilesByFreeRegexStrategy(),
        FindRelatedFilesByFileExtensionStrategy()
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

        val project = checkNotNull(actionEvent.project) { "Project is a precondition" }
        val editor = checkNotNull(FileEditorManager.getInstance(project).selectedTextEditor) { "Editor is a precondition" }
        val origin = checkNotNull(File().activeOn(actionEvent)) { "Active file as origin of action is a precondition" }

        val relations = RelatedFilesWithin(
            origin,
            ProjectFiles(project).all(),
            settings,
            relatedFilesStrategies
        ).find()

        if (relations.isEmpty()) {
            ShowNoRelationsFoundHint(actionEvent).show()
            return
        }

        val validRelations = FilterInvalidRelations(relations, project).filter()
        val deduplicatedRelations = DeduplicateRelations(validRelations, deduplicationStrategy).deduplicate()
        val groupedRelations = GroupRelations(deduplicatedRelations, groupStrategy).group()
        val orderedRelationGroups =
            OrderRelationGroups(groupedRelations, relationOrderStrategy, relationGroupOrderStrategy, settings).arrange()

        val relationsForPopup = PopupRelations(orderedRelationGroups, PopupContentConverter(), project).arrange()
        if (relationsForPopup.hasOnlyOneChoice()) {
            NavigateTo(relationsForPopup.firstChoice).directly()
            return
        }

        ShowRelatedFiles(relationsForPopup, editor).popUp()
    }

    private val settings get() = SettingsPersistentStateComponent.getInstance().state
}
