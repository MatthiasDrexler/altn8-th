package de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.interaction.user

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.AbstractCell
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.popup.visualization.cell.FileCell


fun navigateToFile(): (AbstractCell) -> Unit =
    { cell ->
        if (cell is FileCell) {
            cell.psiFile.navigate(true)
        }
    }
