package de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.implementations

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.fileEditor.FileEditor
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.Precondition

class EditorIsAvailablePrecondition : Precondition {
    override fun notFulfilled(actionEvent: AnActionEvent) =
        actionEvent.getData(PlatformCoreDataKeys.FILE_EDITOR) !is FileEditor

    override fun handleFor(actionEvent: AnActionEvent) {
        // do nothing
    }
}
