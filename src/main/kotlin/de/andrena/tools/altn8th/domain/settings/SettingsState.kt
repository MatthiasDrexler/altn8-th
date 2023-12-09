package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.BaseState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import kotlinx.serialization.Serializable

@Serializable
class SettingsState : BaseState() {
    var prefixes: MutableList<PrefixSetting>
            by list()

    var postfixes: MutableList<PostfixSetting>
            by list()

    var excludedFileExtensions: MutableList<String>
            by list()

    init {
        prefixes.addAll(initialTest())
        postfixes.addAll(initialPostfixes())
        excludedFileExtensions.addAll(initialExcludedFileExtensions())
    }

    private fun initialTest() = listOf(
        PrefixSetting("I(?=[A-Z])", "Interfaces"),
        PrefixSetting("Abstract(?=[A-Z])", "Abstract classes"),
        PrefixSetting("[Tt]est_?", "Test classes"),
    )

    private fun initialPostfixes() = listOf(
        PostfixSetting("([Uu]nit|[Ii]ntegration)?[Tt]ests?", "Test classes"),
        PostfixSetting("[Ii]mpl", "Implementations"),
        PostfixSetting("[Rr]epository", "Repositories"),
        PostfixSetting("[Hh]andler", "Handlers"),
        PostfixSetting("[Bb]uilder", "Builders"),
        PostfixSetting("[Ff]actory", "Factories"),
        PostfixSetting("[Cc]ontroller", "Controllers"),
        PostfixSetting("[Rr]esource|[Rr]equest|[Rr]esponse|[Dd]to|DTO", "Data Transfer Objects"),
        PostfixSetting("([Dd][Bb])?[Ee]ntity", "Entities"),
    )

    private fun initialExcludedFileExtensions() = listOf(
        "bkp",
        ".*~",
    )
}