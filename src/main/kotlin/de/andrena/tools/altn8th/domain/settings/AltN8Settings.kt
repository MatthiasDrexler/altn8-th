package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service


@State(
    name = "de.andrena.tools.altn8th.AltN8Settings",
    storages = [Storage("altn8-th.xml")]
)
internal class AltN8Settings : SimplePersistentStateComponent<AltN8SettingsState>(AltN8SettingsState()) {
    companion object {
        @JvmStatic
        fun getInstance() = service<AltN8Settings>()
    }
}
