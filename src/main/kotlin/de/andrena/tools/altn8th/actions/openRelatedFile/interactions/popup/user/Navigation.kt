package de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.user

import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.interactions.popup.cell.FileCell


internal fun navigateToFile(): (AbstractCell) -> Unit =
    { cell ->
        if (cell is FileCell) {
            cell.psiFile.navigate(true)
        }
    }
