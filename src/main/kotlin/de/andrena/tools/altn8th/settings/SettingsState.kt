package de.andrena.tools.altn8th.settings

import com.intellij.openapi.components.BaseState

internal class SettingsState : BaseState() {
    var isEnabled: Boolean
            by property(true)

    var postfixes: MutableList<String>
            by list()
}