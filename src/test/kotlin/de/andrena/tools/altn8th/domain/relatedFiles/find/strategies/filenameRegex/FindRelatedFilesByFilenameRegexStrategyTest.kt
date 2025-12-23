package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filenameRegex

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilenameRegexSetting
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FindRelatedFilesByFilenameRegexStrategyTest {
    @Nested
    inner class FindRelatedFiles {
        val caseInsensitive = true
        val caseSensitive = false

        @Test
        fun `should relate files by filename relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filenameRegexSetting = FilenameRegexSetting("source", "destination", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should handle filenames with special regex characters`() {
            val originFile = File.from("/is/origin/[source]")
            val relatedFile = File.from("/is/related/[destination]")
            val filenameRegexSetting = FilenameRegexSetting("\\[source\\]", "\\[destination\\]", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files case-insensitively by filename relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filenameRegexSetting = FilenameRegexSetting("Source", "Destination", "Category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseInsensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files by filename relations for regex destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filenameRegexSetting = FilenameRegexSetting("source", "destination[\\w]*", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files by filename relations for regex origin`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destination")
            val filenameRegexSetting = FilenameRegexSetting("source[\\w]*", "destination", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files by filename relations for regex origin and destination`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filenameRegexSetting = FilenameRegexSetting("source[\\w]*", "destination[\\w]*", "category")

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files using single named group reference`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Component.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{name}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should not relate files when named group does not match`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Service.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{name}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result).isEqualTo(null)
        }

        @Test
        fun `should relate files using multiple named group references`() {
            val originFile = File.from("/project/UserServiceTest.java")
            val relatedFile = File.from("/project/UserService.java")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)(?<suffix>Test)\\.(?<ext>.*)",
                "#{name}\\.#{ext}",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should handle named groups with special regex characters in captured values`() {
            val originFile = File.from("/project/[Component]Test.kt")
            val relatedFile = File.from("/project/[Component].kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{name}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should work with case insensitive matching`() {
            val originFile = File.from("/project/componenttest.kt")
            val relatedFile = File.from("/project/component.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)TEST\\.KT",
                "#{name}\\.KT",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseInsensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should handle escaped hash signs as literal text`() {
            val originFile = File.from("/project/Component#1Test.kt")
            val relatedFile = File.from("/project/Component#1.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{name}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should gracefully handle non-existent named group by substituting with empty string`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{nonexistent}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should work without named groups for backward compatibility`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Component.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "ComponentTest\\.kt",
                "Component\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should reuse same named group multiple times`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Component_Component_backup.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{name}_#{name}_backup\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should handle empty captured groups`() {
            val originFile = File.from("Test.kt")
            val relatedFile = File.from(".kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<prefix>.*?)Test\\.kt",
                "#{prefix}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should handle dollar signs in filenames without ambiguity`() {
            val originFile = File.from("/project/Component\$Test.kt")
            val relatedFile = File.from("/project/Component\$123.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name>.*)Test\\.kt",
                "#{name}123\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should handle digits in named capturing group identifier`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Component.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<name1>.*)Test\\.kt",
                "#{name1}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilenameRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should ignore underscore in named capturing group identifier`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Component.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<base_name>.*)Test\\.kt",
                "#{base_name}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result).isEqualTo(null)
        }

        @Test
        fun `should ignore dollar sign in named capturing group identifier`() {
            val originFile = File.from("/project/ComponentTest.kt")
            val relatedFile = File.from("/project/Component.kt")
            val filenameRegexSetting = FilenameRegexSetting(
                "(?<base$>.*)Test\\.kt",
                "#{base$}\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilenameRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilenameRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result).isEqualTo(null)
        }

        private fun configuredFilenameRegexes(
            caseInsensitive: Boolean,
            vararg filenameRegexes: FilenameRegexSetting
        ): SettingsState {
            val settings = SettingsState()
            settings.caseInsensitiveMatching = caseInsensitive
            settings.filenameRegexes.clear()
            settings.filenameRegexes.addAll(filenameRegexes)
            return settings
        }
    }
}
