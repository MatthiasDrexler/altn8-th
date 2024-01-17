package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.BaseState
import de.andrena.tools.altn8th.domain.settings.types.FreeRelationSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import kotlinx.serialization.Serializable

@Serializable
class SettingsState : BaseState() {
    var prefixes: MutableList<PrefixSetting>
            by list()

    var postfixes: MutableList<PostfixSetting>
            by list()

    var freeRelations: MutableList<FreeRelationSetting>
        by list()

    var excludedFileExtensions: MutableList<String>
            by list()

    init {
        prefixes.addAll(initialPrefixes())
        postfixes.addAll(initialPostfixes())
        freeRelations.addAll(initialFreeRelations())
        excludedFileExtensions.addAll(initialExcludedFileExtensions())
    }

    private fun initialPrefixes() = listOf(
        PrefixSetting("I(?=[A-Z])", "Interfaces", "Abstraction"),
        PrefixSetting("Abstract(?=[A-Z])", "Abstract classes", "Abstraction"),
        PrefixSetting("[Tt]est_?", "Test classes", "Testing"),
    )

    private fun initialPostfixes() = listOf(
        PostfixSetting("([Uu]nit|[Ii]ntegration)?[Tt]ests?", "Test classes", "Testing"),
        PostfixSetting("[Ii]mpl", "Implementations", "Abstraction"),
        PostfixSetting("[Hh]andler", "Handlers", "Controller"),
        PostfixSetting("[Bb]uilder", "Builders", "Patterns"),
        PostfixSetting("[Ff]actory", "Factories", "Patterns"),
        PostfixSetting("[Cc]ontroller", "Controllers", "Controller"),
        PostfixSetting("[Rr]esource|[Rr]equest|[Rr]esponse|[Dd]to|DTO", "Data Transfer Objects", "Data"),
        PostfixSetting("[Rr]epository", "Repositories", "Data"),
        PostfixSetting("([Dd][Bb])?[Ee]ntity", "Entities", "Data"),
    )

    private fun initialFreeRelations() = listOf(
        FreeRelationSetting("README", "CONTRIBUTE")
    )

    private fun initialExcludedFileExtensions() = listOf(
        "bkp",
        ".*~",
    )
}
