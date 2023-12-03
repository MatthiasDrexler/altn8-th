package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.psi.PsiFile

internal class SelectedRelatedFile(
    private val psiFile: PsiFile
) {
    fun open() = psiFile.navigate(true)
}