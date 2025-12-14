package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.vfs.VirtualFile
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.Precondition

class FileIsOpenedPrecondition : Precondition {
    override fun isNotFulfilled(actionEvent: AnActionEvent) =
        actionEvent.getData(PlatformCoreDataKeys.VIRTUAL_FILE) !is VirtualFile
}
