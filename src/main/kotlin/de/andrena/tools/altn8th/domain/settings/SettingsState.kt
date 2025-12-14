package de.andrena.tools.altn8th.domain.settings

import com.intellij.openapi.components.BaseState
import com.intellij.util.xmlb.annotations.OptionTag
import de.andrena.tools.altn8th.domain.settings.types.FilePathRegexSetting
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import de.andrena.tools.altn8th.internationalization.I18n
import kotlinx.serialization.Serializable


@Serializable
class SettingsState : BaseState() {
    companion object {
        private val ABSTRACTION = I18n.lazyMessage("altn8.default.category.abstraction")
        private val CONTRIBUTION = I18n.lazyMessage("altn8.default.category.contribution")
        private val DATA_TRANSFER = I18n.lazyMessage("altn8.default.category.dataTransfer")
        private val HANDLER = I18n.lazyMessage("altn8.default.category.handler")
        private val PATTERNS = I18n.lazyMessage("altn8.default.category.patterns")
        private val PERSISTENCE = I18n.lazyMessage("altn8.default.category.persistence")
        private val SERVICE = I18n.lazyMessage("altn8.default.category.service")
        private val TESTING = I18n.lazyMessage("altn8.default.category.testing")
        private val AI = I18n.lazyMessage("altn8.default.category.ai")

        private val INTERFACES = I18n.lazyMessage("altn8.default.description.interfaces")
        private val ABSTRACT_CLASSES = I18n.lazyMessage("altn8.default.description.abstractClasses")
        private val TEST_CLASSES = I18n.lazyMessage("altn8.default.description.testClasses")
        private val IMPLEMENTATIONS = I18n.lazyMessage("altn8.default.description.implementations")
        private val SERVICES = I18n.lazyMessage("altn8.default.category.service")
        private val CONTROLLERS = I18n.lazyMessage("altn8.default.description.controllers")
        private val HANDLERS = I18n.lazyMessage("altn8.default.description.handlers")
        private val DATA_TRANSFER_OBJECTS = I18n.lazyMessage("altn8.default.description.dataTransferObjects")
        private val REPOSITORIES = I18n.lazyMessage("altn8.default.description.repositories")
        private val ENTITIES = I18n.lazyMessage("altn8.default.description.entities")
        private val BUILDERS = I18n.lazyMessage("altn8.default.description.builders")
        private val FACTORIES = I18n.lazyMessage("altn8.default.description.factories")
    }

    var prefixes: MutableList<PrefixSetting> by list()

    var postfixes: MutableList<PostfixSetting> by list()

    @get:OptionTag("freeRegexes")
    var filenameRegexes: MutableList<FilenameRegexSetting> by list()

    var filePathRegexes: MutableList<FilePathRegexSetting> by list()

    var excludedFileExtensions: MutableList<String> by list()

    var caseInsensitiveMatching: Boolean by property(false)

    init {
        prefixes.addAll(initialPrefixes())
        postfixes.addAll(initialPostfixes())
        filenameRegexes.addAll(initialFilenameRegex())
        filePathRegexes.addAll(initialFilePathRegex())
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
        PostfixSetting(
            """[Rr]esource|[Rr]equest|[Rr]esponse|[Dd]to|DTO""",
            DATA_TRANSFER_OBJECTS.get(),
            DATA_TRANSFER.get()
        ),
        PostfixSetting("""[Rr]epository""", REPOSITORIES.get(), PERSISTENCE.get()),
        PostfixSetting("""([Dd][Bb]|Database)?[Ee]ntity""", ENTITIES.get(), PERSISTENCE.get()),
        PostfixSetting("""[Bb]uilder""", BUILDERS.get(), PATTERNS.get()),
        PostfixSetting("""[Ff]actory""", FACTORIES.get(), PATTERNS.get()),
    )

    private fun initialFilenameRegex() = listOf(
        FilenameRegexSetting(
            """README(?<fileExtension>\.[\w]*)?""", """CONTRIBUTE(?<fileExtension>\.[\w]*)?""", CONTRIBUTION.get()
        )
    )

    private fun initialFilePathRegex() = listOf(
        FilePathRegexSetting(
            """(?<github>.*)/copilot-instructions\.md""",
            """#{github}/instructions/\w*\.instructions\.md""",
            TESTING.get()
        )
    )

    private fun initialExcludedFileExtensions() = listOf(
        "bkp",
        ".*~",
    )
}
