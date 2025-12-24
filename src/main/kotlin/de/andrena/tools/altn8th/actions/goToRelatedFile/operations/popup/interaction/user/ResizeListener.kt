package de.andrena.tools.altn8th.actions.goToRelatedFile.operations.popup.interaction.user

import com.intellij.openapi.ui.popup.JBPopup
import de.andrena.tools.altn8th.internationalization.I18n
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.SwingConstants

class ResizeListener(private val popup: JBPopup) : ComponentAdapter() {
    companion object {
        private val ACTION_DESCRIPTION = I18n.lazyMessage("altn8.relations.popup.action")
    }

    override fun componentResized(e: ComponentEvent?) {
        popup.setAdText(ACTION_DESCRIPTION.get(), SwingConstants.LEADING)
        popup.pack(true, true)
    }
}
