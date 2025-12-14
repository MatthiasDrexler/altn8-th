package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.implementations

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.fileEditor.FileEditor
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.Precondition

class EditorIsAvailablePrecondition : Precondition {
    override fun isNotFulfilled(actionEvent: AnActionEvent) =
        actionEvent.getData(PlatformCoreDataKeys.FILE_EDITOR) !is FileEditor
}
