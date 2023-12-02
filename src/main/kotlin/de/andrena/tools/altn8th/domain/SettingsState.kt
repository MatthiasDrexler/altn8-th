package de.andrena.tools.altn8th.domain

import com.intellij.openapi.components.BaseState

class SettingsState : BaseState() {
    var isEnabled: Boolean
            by property(true)

    var prefixes: MutableList<String>
            by list()

    var postfixes: MutableList<String>
            by list()

    var excludedFileExtensions: MutableList<String>
            by list()

    init {
        prefixes.addAll(initialPrefixes())
        postfixes.addAll(initialPostfixes())
        excludedFileExtensions.addAll(initialExcludedFileExtensions())
    }

    private fun initialPrefixes() = listOf(
        "I(?=[A-Z])",
        "Abstract(?=[A-Z])",
        "[Tt]est_?",
    )

    private fun initialPostfixes() = listOf(
        "([Uu]nit|[Ii]ntegration)?[Tt]ests?",
        "[Ii]mpl",
        "[Rr]epository",
        "[Hh]andler",
        "[Bb]uilder",
        "[Ff]actory",
        "[Cc]ontroller",
        "[Rr]esource|[Rr]equest|[Rr]esponse|[Dd]to|DTO",
    )

    private fun initialExcludedFileExtensions() = listOf(
        "bkp",
        ".*~",
    )
}