package de.andrena.tools.altn8th.actions.openRelatedFile.handler

import com.intellij.psi.PsiFile

internal class OpenRelatedFile(
    private val psiFile: PsiFile
) {
    fun open() = psiFile.navigate(true)
}