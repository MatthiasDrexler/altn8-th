package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class FindRelatedFilesByPrefixStrategyTest {
    @Nested
    inner class FindRelatedFiles {
        val CaseInsensitive = true
        val CaseSensitive = false

        @Test
        fun `should relate files with relating prefixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/Abstract${origin.nameWithoutFileExtension()}.kt")
            val prefixSetting = createPrefixSetting("Abstract")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(PrefixRegexRelation.from(relatedFile, origin, null, prefixSetting))
            }
        }

        @Test
        fun `should relate files case-insensitively with relating prefixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/Abstract${origin.nameWithoutFileExtension()}.kt")
            val prefixSetting = createPrefixSetting("abstract")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseInsensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(PrefixRegexRelation.from(relatedFile, origin, null, prefixSetting))
            }
        }

        @Test
        fun `should relate files with relating regex prefixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/Test${origin.nameWithoutFileExtension()}.kt")
            val prefixSetting = createPrefixSetting("(Unit)?Tests?")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(PrefixRegexRelation.from(relatedFile, origin, null, prefixSetting))
            }
        }

        @Test
        fun `should handle filenames with special regex characters`() {
            val origin = File.from("/is/origin/[Origin].kt")
            val relatedFile = File.from("/is/related/Test[Origin].kt")
            val prefixSetting = createPrefixSetting("(Unit)?Tests?")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(PrefixRegexRelation.from(relatedFile, origin, null, prefixSetting))
            }
        }

        @Test
        fun `should relate files from files with relating prefixes`() {
            val baseFile = File.from("/is/related/Base.kt")
            val origin = File.from("/is/origin/Test${baseFile.nameWithoutFileExtension()}.kt")
            val prefixSetting = createPrefixSetting("Tests?")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                baseFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(PrefixRegexRelation.from(baseFile, origin, null, prefixSetting))
            }
        }

        @Test
        fun `should relate files from files with relating regex prefixes`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/Test${baseFile.nameWithoutFileExtension()}.kt")
            val prefixSetting = createPrefixSetting("(Unit)?Tests?")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                baseFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(PrefixRegexRelation.from(baseFile, origin, null, prefixSetting))
            }
        }

        @Test
        fun `should relate files with prefix with other files with relating prefixes`() {
            val baseName = "Origin"
            val origin = File.from("/is/origin/Test${baseName}.kt")
            val relatedFile = File.from("/is/related/Abstract${baseName}.kt")
            val prefixSettingMatchingOrigin = createPrefixSetting("Test")
            val prefixSettingMatchingRelated = createPrefixSetting("Abstract")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSettingMatchingRelated, prefixSettingMatchingOrigin)
            )

            expectThat(result) {
                isEqualTo(
                    PrefixRegexRelation.from(
                        relatedFile,
                        origin,
                        prefixSettingMatchingOrigin, prefixSettingMatchingRelated
                    )
                )
            }
        }

        @Test
        fun `should relate files with prefix with other files with relating regex prefixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/Test${baseName}.ts")
            val relatedFile = File.from("/is/related/UnitTest${baseName}.ts")
            val prefixSetting = createPrefixSetting("[\\w]*Tests?")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result) {
                isEqualTo(
                    PrefixRegexRelation.from(
                        relatedFile,
                        origin,
                        prefixSetting, prefixSetting
                    )
                )
            }
        }

        @Test
        fun `should not relate itself from base file as origin`() {
            val origin = File.from("/is/origin/OriginTest.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                origin,
                configuredPrefixes(
                    CaseSensitive,
                    createPrefixSetting("Test")
                )
            )


            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not relate files with some postfix`() {
            val origin = File.from("/is/origin/Origin.kt")
            val unrelatedFile = File.from("/is/unrelated/Test${origin.nameWithoutFileExtension()}Unrelated.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                unrelatedFile,
                configuredPrefixes(CaseSensitive, createPrefixSetting("Test"))
            )

            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not relate files without relating prefixes`() {
            val base = File.from("/is/origin/Origin.kt")
            val origin = File.from("/is/unrelated/Unrelated${base.nameWithoutFileExtension()}.kt")
            val unrelatedFile = File.from("/is/unrelated/UnrelatedTest${base.nameWithoutFileExtension()}.kt")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                unrelatedFile,
                configuredPrefixes(CaseSensitive, createPrefixSetting("Test"))
            )

            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not crash with unclosed bracket in prefix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val prefixSetting = createPrefixSetting("[Test")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with unclosed group in prefix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val prefixSetting = createPrefixSetting("(Test")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with dangling metacharacter in prefix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val prefixSetting = createPrefixSetting("?")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with trailing backslash in prefix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val prefixSetting = createPrefixSetting("""Test\""")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with invalid character class in prefix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val prefixSetting = createPrefixSetting("[z-a]")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with underscore in named group`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val prefixSetting = createPrefixSetting("(?<test_name>Test)")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, prefixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should return null when invalid pattern is present`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/TestOrigin.kt")
            val invalidPrefixSetting = createPrefixSetting("[Test")
            val validPrefixSetting = createPrefixSetting("Test")

            val result = FindRelatedFilesByPrefixStrategy().find(
                origin,
                relatedFile,
                configuredPrefixes(CaseSensitive, invalidPrefixSetting, validPrefixSetting)
            )

            expectThat(result).isNull()
        }

        private fun configuredPrefixes(caseInsensitive: Boolean, vararg prefixes: PrefixSetting): SettingsState {
            val setting = SettingsState()
            setting.caseInsensitiveMatching = caseInsensitive
            setting.prefixes.clear()
            setting.prefixes.addAll(prefixes)
            return setting
        }

        private fun createPrefixSetting(pattern: String) = PrefixSetting(pattern, "", "")
    }
}
