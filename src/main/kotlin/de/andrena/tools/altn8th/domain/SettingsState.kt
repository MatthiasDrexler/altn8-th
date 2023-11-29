package de.andrena.tools.altn8th.domain

import com.intellij.openapi.components.BaseState

class SettingsState : BaseState() {
    var isEnabled: Boolean
            by property(true)

    var postfixes: MutableList<String>
            by list()

    var excludedFileExtensions: MutableList<String>
            by list()
}