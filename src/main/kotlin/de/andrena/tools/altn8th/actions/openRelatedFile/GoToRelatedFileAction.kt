package de.andrena.tools.altn8th.actions.openRelatedFile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.*
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.interaction.ShowNoRelationsFoundHint
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.PopupRelations
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.ShowRelatedFiles
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.categorization.CategorizeByGroupedCategoryRelationsFirst
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
import de.andrena.tools.altn8th.domain.relatedFiles.prioritize.strategies.PrioritizeRelationsByFlattening
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

    private val prioritizationStrategy = PrioritizeRelationsByFlattening()
    private val deduplicationStrategy = DeduplicateRelationsByTakingFirstOccurrence()
    private val categorizationStrategy = CategorizeByGroupedCategoryRelationsFirst()

    override fun actionPerformed(actionEvent: AnActionEvent) {
        val preconditionsAreSatisfied = PreconditionsFor(actionEvent, preconditions).areSatisfied()
        if (!preconditionsAreSatisfied) {
            return
        }

        val project = checkNotNull(actionEvent.project) { "Project is a precondition" }
        val editor = checkNotNull(actionEvent.getRequiredData(CommonDataKeys.EDITOR)) { "Editor is a precondition" }
        val origin = checkNotNull(File().activeOn(actionEvent)) { "Active file as origin of action is a precondition" }

        val relations = RelatedFilesWithin(
            origin,
            ProjectFiles(project).all(),
            SettingsPersistentStateComponent.getInstance().state,
            relatedFilesStrategies
        ).find()

        val relationsFound = AnyRelations(relations).areFound()
        if (!relationsFound) {
            ShowNoRelationsFoundHint(actionEvent).show()
            return
        }

        val prioritizedRelations = PrioritizeRelations(relations, prioritizationStrategy).prioritize()
        val deduplicatedRelations = DeduplicateRelations(prioritizedRelations, deduplicationStrategy).deduplicate()
        val onlyValidRelations = FilterInvalidRelations(deduplicatedRelations, project).filter()

        val relationsForPopup = PopupRelations(onlyValidRelations, project, categorizationStrategy).arrange()
        if (relationsForPopup.hasOnlyOneChoice()) {
            NavigateTo(relationsForPopup.firstChoice).directly()
            return
        }

        ShowRelatedFiles(relationsForPopup, editor).popUp()
    }
}
