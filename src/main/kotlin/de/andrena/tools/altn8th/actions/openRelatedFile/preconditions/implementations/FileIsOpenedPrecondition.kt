package de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.implementations

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.vfs.VirtualFile
import de.andrena.tools.altn8th.actions.openRelatedFile.preconditions.Precondition

internal class FileIsOpenedPrecondition : Precondition {
    override fun notFulfilled(actionEvent: AnActionEvent) =
        actionEvent.getData(PlatformCoreDataKeys.VIRTUAL_FILE) !is VirtualFile

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}
