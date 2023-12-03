package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.project.Project
import de.andrena.tools.altn8th.adapter.ProjectFiles
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType
import de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.FindRelatedFilesStrategy
import de.andrena.tools.altn8th.settings.SettingsPersistentStateComponent

internal class RelatedFilesFrom(
    private val origin: File,
    private val project: Project,
    private val relatedFilesStrategies: Collection<FindRelatedFilesStrategy>
) {
    fun find(): List<RelationsByType> {
        val allFiles = ProjectFiles().allOf(project)
        val settings = SettingsPersistentStateComponent.getInstance().state

        return relatedFilesStrategies.map { it.find(origin, allFiles, settings) }
    }
}