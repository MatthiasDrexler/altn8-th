package de.andrena.tools.altn8th.actions.goToRelated.preconditions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.vfs.VirtualFile

internal class FileIsOpenedPrecondition : Precondition {
    override fun isNotFulfilled(actionEvent: AnActionEvent) =
        actionEvent.getData(PlatformCoreDataKeys.VIRTUAL_FILE) !is VirtualFile

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}
