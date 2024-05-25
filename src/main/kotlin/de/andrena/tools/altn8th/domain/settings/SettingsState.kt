package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.BaseState
import de.andrena.tools.altn8th.domain.settings.types.FreeRelationSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import kotlinx.serialization.Serializable

@Serializable
class SettingsState : BaseState() {
    companion object {
        private const val ABSTRACTION = "Abstraction"
        private const val CONTRIBUTION = "Contribution"
        private const val DATA_TRANSFER = "Data Transfer"
        private const val HANDLER = "Handler"
        private const val PATTERNS = "Patterns"
        private const val PERSISTENCE = "Persistence"
        private const val SERVICE = "Service"
        private const val TESTING = "Testing"
    }

    var prefixes: MutableList<PrefixSetting> by list()

    var postfixes: MutableList<PostfixSetting> by list()

    var freeRelations: MutableList<FreeRelationSetting> by list()

    var excludedFileExtensions: MutableList<String> by list()

    init {
        prefixes.addAll(initialPrefixes())
        postfixes.addAll(initialPostfixes())
        freeRelations.addAll(initialFreeRelations())
        excludedFileExtensions.addAll(initialExcludedFileExtensions())
    }

    private fun initialPrefixes() = listOf(
        PrefixSetting("""I(?=[A-Z])""", "Interfaces", ABSTRACTION),
        PrefixSetting("""Abstract(?=[A-Z])""", "Abstract classes", ABSTRACTION),
        PrefixSetting("""[Tt]est_?""", "Test classes", TESTING),
    )

    private fun initialPostfixes() = listOf(
        PostfixSetting("""[Ii]mpl""", "Implementations", ABSTRACTION),
        PostfixSetting("""([Uu]nit|[Ii]ntegration)?[Tt]ests?""", "Test classes", TESTING),
        PostfixSetting("""[Ss]ervice""", "Services", SERVICE),
        PostfixSetting("""[Cc]ontroller""", "Controllers", DATA_TRANSFER),
        PostfixSetting("""[Hh]andler""", "Handlers", HANDLER),
        PostfixSetting("""[Rr]esource|[Rr]equest|[Rr]esponse|[Dd]to|DTO""", "Data Transfer Objects", DATA_TRANSFER),
        PostfixSetting("""[Rr]epository""", "Repositories", PERSISTENCE),
        PostfixSetting("""([Dd][Bb]|Database)?[Ee]ntity""", "Entities", PERSISTENCE),
        PostfixSetting("""[Bb]uilder""", "Builders", PATTERNS),
        PostfixSetting("""[Ff]actory""", "Factories", PATTERNS),
    )

    private fun initialFreeRelations() = listOf(
        FreeRelationSetting(
            """README(?<fileExtension>\.[\w]*)?""", """CONTRIBUTE(?<fileExtension>\.[\w]*)?""", CONTRIBUTION
        )
    )

    private fun initialExcludedFileExtensions() = listOf(
        "bkp",
        ".*~",
    )
}
