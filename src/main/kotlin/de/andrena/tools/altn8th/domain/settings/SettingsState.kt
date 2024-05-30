package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.BaseState
import de.andrena.tools.altn8th.domain.settings.types.FreeRegexSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import de.andrena.tools.altn8th.internationalization.I18n
import kotlinx.serialization.Serializable

private val ABSTRACTION = I18n.lazyMessage("altn8.default.category.abstraction")
private val CONTRIBUTION = I18n.lazyMessage("altn8.default.category.contribution")
private val DATA_TRANSFER = I18n.lazyMessage("altn8.default.category.dataTransfer")
private val HANDLER = I18n.lazyMessage("altn8.default.category.handler")
private val PATTERNS = I18n.lazyMessage("altn8.default.category.patterns")
private val PERSISTENCE = I18n.lazyMessage("altn8.default.category.persistence")
private val SERVICE = I18n.lazyMessage("altn8.default.category.service")
private val TESTING = I18n.lazyMessage("altn8.default.category.testing")

private val INTERFACES = I18n.lazyMessage("altn8.default.interfaces")
private val ABSTRACT_CLASSES = I18n.lazyMessage("altn8.default.abstractClasses")
private val TEST_CLASSES = I18n.lazyMessage("altn8.default.testClasses")
private val IMPLEMENTATIONS = I18n.lazyMessage("altn8.default.implementations")
private val SERVICES = I18n.lazyMessage("altn8.default.category.service")
private val CONTROLLERS = I18n.lazyMessage("altn8.default.controllers")
private val HANDLERS = I18n.lazyMessage("altn8.default.handlers")
private val DATA_TRANSFER_OBJECTS = I18n.lazyMessage("altn8.default.dataTransferObjects")
private val REPOSITORIES = I18n.lazyMessage("altn8.default.repositories")
private val ENTITIES = I18n.lazyMessage("altn8.default.entities")
private val BUILDERS = I18n.lazyMessage("altn8.default.builders")
private val FACTORIES = I18n.lazyMessage("altn8.default.factories")

@Serializable
class SettingsState : BaseState() {
    var prefixes: MutableList<PrefixSetting> by list()

    var postfixes: MutableList<PostfixSetting> by list()

    var freeRegexes: MutableList<FreeRegexSetting> by list()

    var excludedFileExtensions: MutableList<String> by list()

    init {
        prefixes.addAll(initialPrefixes())
        postfixes.addAll(initialPostfixes())
        freeRegexes.addAll(initialFreeRegex())
        excludedFileExtensions.addAll(initialExcludedFileExtensions())
    }

    private fun initialPrefixes() = listOf(
        PrefixSetting("""I(?=[A-Z])""", INTERFACES.get(), ABSTRACTION.get()),
        PrefixSetting("""Abstract(?=[A-Z])""", ABSTRACT_CLASSES.get(), ABSTRACTION.get()),
        PrefixSetting("""[Tt]est_?""", TEST_CLASSES.get(), TESTING.get()),
    )

    private fun initialPostfixes() = listOf(
        PostfixSetting("""[Ii]mpl""", IMPLEMENTATIONS.get(), ABSTRACTION.get()),
        PostfixSetting("""([Uu]nit|[Ii]ntegration)?[Tt]ests?""", TEST_CLASSES.get(), TESTING.get()),
        PostfixSetting("""[Ss]ervice""", SERVICES.get(), SERVICE.get()),
        PostfixSetting("""[Cc]ontroller""", CONTROLLERS.get(), DATA_TRANSFER.get()),
        PostfixSetting("""[Hh]andler""", HANDLERS.get(), HANDLER.get()),
        PostfixSetting("""[Rr]esource|[Rr]equest|[Rr]esponse|[Dd]to|DTO""", DATA_TRANSFER_OBJECTS.get(), DATA_TRANSFER.get()),
        PostfixSetting("""[Rr]epository""", REPOSITORIES.get(), PERSISTENCE.get()),
        PostfixSetting("""([Dd][Bb]|Database)?[Ee]ntity""", ENTITIES.get(), PERSISTENCE.get()),
        PostfixSetting("""[Bb]uilder""", BUILDERS.get(), PATTERNS.get()),
        PostfixSetting("""[Ff]actory""", FACTORIES.get(), PATTERNS.get()),
    )

    private fun initialFreeRegex() = listOf(
        FreeRegexSetting(
            """README(?<fileExtension>\.[\w]*)?""", """CONTRIBUTE(?<fileExtension>\.[\w]*)?""", CONTRIBUTION.get()
        )
    )

    private fun initialExcludedFileExtensions() = listOf(
        "bkp",
        ".*~",
    )
}
