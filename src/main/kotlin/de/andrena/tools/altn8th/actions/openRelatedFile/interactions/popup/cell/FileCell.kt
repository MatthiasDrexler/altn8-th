package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell

import com.intellij.psi.PsiFile

internal data class FileCell(val psiFile: PsiFile) : AbstractCell() {
    override fun readableRepresentation(): String = psiFile.name
}