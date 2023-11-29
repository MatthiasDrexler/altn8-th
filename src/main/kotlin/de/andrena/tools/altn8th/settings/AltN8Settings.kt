package de.andrena.tools.altn8th.settings

import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import de.andrena.tools.altn8th.domain.SettingsState


@State(
    name = "de.andrena.tools.altn8th.AltN8Settings",
    storages = [Storage("altn8-th.xml")]
)
class AltN8Settings : SimplePersistentStateComponent<SettingsState>(SettingsState()) {
    companion object {
        @JvmStatic
        fun getInstance() = service<AltN8Settings>()
    }
}
