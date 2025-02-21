package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@RunWith(Enclosed::class)
class FindRelatedFilesByPrefixStrategyTest {
    class FindRelatedFiles {
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
                configuredPrefixes(CaseSensitive,prefixSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, origin, PrefixRelationType(null, prefixSetting)))
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
                configuredPrefixes(CaseInsensitive,prefixSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, origin, PrefixRelationType(null, prefixSetting)))
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
                configuredPrefixes(CaseSensitive,prefixSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, origin, PrefixRelationType(null, prefixSetting)))
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
                configuredPrefixes(CaseSensitive,prefixSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(relatedFile, origin, PrefixRelationType(null, prefixSetting)))
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
                configuredPrefixes(CaseSensitive,prefixSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(baseFile, origin, PrefixRelationType(null, prefixSetting)))
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
                configuredPrefixes(CaseSensitive,prefixSetting)
            )

            expectThat(result) {
                isEqualTo(Relation(baseFile, origin, PrefixRelationType(null, prefixSetting)))
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
                    Relation(
                        relatedFile,
                        origin,
                        PrefixRelationType(prefixSettingMatchingOrigin, prefixSettingMatchingRelated)
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
                    Relation(
                        relatedFile,
                        origin,
                        PrefixRelationType(prefixSetting, prefixSetting)
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
