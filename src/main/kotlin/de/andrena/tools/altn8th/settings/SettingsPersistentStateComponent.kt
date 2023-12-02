package de.andrena.tools.altn8th.settings

import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import de.andrena.tools.altn8th.domain.settings.SettingsState


@State(
    name = "AltN8-TH",
    storages = [Storage("altn8-th.xml")]
)
class SettingsPersistentStateComponent : SimplePersistentStateComponent<SettingsState>(SettingsState()) {
    companion object {
        @JvmStatic
        fun getInstance() = service<SettingsPersistentStateComponent>()
    }
}
