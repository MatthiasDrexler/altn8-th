package de.andrena.tools.altn8th.actions.openRelatedFile.handler

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.adapter.interaction.InlineHint
import de.andrena.tools.altn8th.adapter.jetbrains.JetBrainsPsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByType

internal class SelectRelatedFile(
    private val actionEvent: AnActionEvent,
    private val relations: Collection<RelationsByType>,
    private val project: Project
) {
    fun select(): PsiFile? {
        val psiFiles = relations.flatMap { it.relatedFiles }
            .mapNotNull { FileConverter().toVirtualFile(it) }
            .map { JetBrainsPsiFile().findFor(it, project) }

        if (psiFiles.isEmpty()) {
            InlineHint().showFor(actionEvent, "No corresponding file(s) found")
            return null
        }

        return psiFiles.first()
    }
}
