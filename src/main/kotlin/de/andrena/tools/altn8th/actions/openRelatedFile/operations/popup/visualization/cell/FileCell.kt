package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell

import com.intellij.psi.PsiFile
import de.andrena.tools.altn8th.domain.relatedFiles.Relation

internal data class FileCell(val relation: Relation, val psiFile: PsiFile) : AbstractCell() {
    override fun cellText(): String = psiFile.name
    override fun tooltipText(): String = relation.type.explanation()
}
