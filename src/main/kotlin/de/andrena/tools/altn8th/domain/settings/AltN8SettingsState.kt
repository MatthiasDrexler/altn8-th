package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.BaseState

internal class AltN8SettingsState : BaseState() {
    var isEnabled by property(true)
}