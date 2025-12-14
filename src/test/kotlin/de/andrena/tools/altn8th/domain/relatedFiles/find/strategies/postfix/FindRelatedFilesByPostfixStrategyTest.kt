package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@RunWith(Enclosed::class)
class FindRelatedFilesByPostfixStrategyTest {
    class FindRelatedFiles {
        val caseInsensitive = true
        val caseSensitive = false

        @Test
        fun `should relate files with relating postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val postfixSetting = createPostfixSetting("Test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result) {
                isEqualTo(PostfixRegexRelation.from(relatedFile, origin, null, postfixSetting))
            }
        }

        @Test
        fun `should relate files case-insensitively with relating postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val postfixSetting = createPostfixSetting("test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseInsensitive, postfixSetting)
            )

            expectThat(result) {
                isEqualTo(PostfixRegexRelation.from(relatedFile, origin, null, postfixSetting))
            }
        }

        @Test
        fun `should relate files with relating regex postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/${origin.nameWithoutFileExtension()}Test.kt")
            val postfixSetting = createPostfixSetting("(Unit)?Tests?")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result) {
                isEqualTo(PostfixRegexRelation.from(relatedFile, origin, null, postfixSetting))
            }
        }

        @Test
        fun `should relate files from files with relating postfixes`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val postfixSetting = createPostfixSetting("Test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                baseFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result) {
                isEqualTo(PostfixRegexRelation.from(baseFile, origin, null, postfixSetting))
            }
        }

        @Test
        fun `should handle filenames with special regex characters`() {
            val origin = File.from("/is/base/[Origin].kt")
            val relatedFile = File.from("/is/related/[Origin]Test.kt")
            val postfixSetting = createPostfixSetting("Test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, createPostfixSetting("Test"))
            )

            expectThat(result) {
                isEqualTo(
                    PostfixRegexRelation.from(
                        relatedFile,
                        origin,
                        null,
                        postfixSetting
                    )
                )
            }
        }

        @Test
        fun `should relate files from files with relating regex postfixes`() {
            val baseFile = File.from("/is/base/Origin.kt")
            val origin = File.from("/is/origin/${baseFile.nameWithoutFileExtension()}Test.kt")
            val postfixSetting = createPostfixSetting("(Unit)?Tests?")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                baseFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result) {
                isEqualTo(PostfixRegexRelation.from(baseFile, origin, null, postfixSetting))
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating postfixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/${baseName}.component.ts")
            val relatedFile = File.from("/is/related/${baseName}.service.ts")
            val postfixSettingMatchingOrigin = createPostfixSetting("\\.component")
            val postfixSettingMatchingRelatedFile = createPostfixSetting("\\.service")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSettingMatchingRelatedFile, postfixSettingMatchingOrigin)
            )

            expectThat(result) {
                isEqualTo(
                    PostfixRegexRelation.from(
                        relatedFile,
                        origin,
                        postfixSettingMatchingOrigin,
                        postfixSettingMatchingRelatedFile
                    )
                )
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating regex postfixes`() {
            val baseName = "origin"
            val origin = File.from("/is/origin/${baseName}.component.ts")
            val relatedFile = File.from("/is/related/${baseName}.todo-store.ts")
            val postfixSettingMatchingOrigin = createPostfixSetting("\\.component")
            val postfixSettingMatchingRelatedFile = createPostfixSetting("\\.[\\-\\w]*store")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSettingMatchingRelatedFile, postfixSettingMatchingOrigin)
            )

            expectThat(result) {
                isEqualTo(
                    PostfixRegexRelation.from(
                        relatedFile,
                        origin,
                        postfixSettingMatchingOrigin,
                        postfixSettingMatchingRelatedFile
                    )
                )
            }
        }

        @Test
        fun `should not relate itself from base file as origin`() {
            val origin = File.from("/is/origin/OriginTest.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                origin,
                configuredPostfixes(caseSensitive, createPostfixSetting("Test"))
            )

            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not relate files with some prefix`() {
            val origin = File.from("/is/origin/Origin.kt")
            val unrelatedFile = File.from("/is/unrelated/Unrelated${origin.nameWithoutFileExtension()}Test.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                unrelatedFile,
                configuredPostfixes(caseSensitive, createPostfixSetting("Test"))
            )

            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not relate files without relating postfixes`() {
            val origin = File.from("/is/origin/Origin.kt")
            val unrelatedFile = File.from("/is/unrelated/${origin.nameWithoutFileExtension()}Unrelated.kt")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                unrelatedFile,
                configuredPostfixes(caseSensitive, createPostfixSetting("Test"))
            )

            expectThat(result) {
                isNull()
            }
        }

        @Test
        fun `should not crash with unclosed bracket in postfix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val postfixSetting = createPostfixSetting("[Test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with unclosed group in postfix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val postfixSetting = createPostfixSetting("(Test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with dangling metacharacter in postfix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val postfixSetting = createPostfixSetting("?")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with trailing backslash in postfix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val postfixSetting = createPostfixSetting("""Test\""")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with invalid character class in postfix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val postfixSetting = createPostfixSetting("[z-a]")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should not crash with underscore in named group in postfix pattern`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val postfixSetting = createPostfixSetting("(?<test_name>Test)")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, postfixSetting)
            )

            expectThat(result).isNull()
        }

        @Test
        fun `should return null when invalid postfix pattern is present`() {
            val origin = File.from("/is/origin/Origin.kt")
            val relatedFile = File.from("/is/related/OriginTest.kt")
            val invalidPostfixSetting = createPostfixSetting("[Test")
            val validPostfixSetting = createPostfixSetting("Test")

            val result = FindRelatedFilesByPostfixStrategy().find(
                origin,
                relatedFile,
                configuredPostfixes(caseSensitive, invalidPostfixSetting, validPostfixSetting)
            )

            expectThat(result).isNull()
        }

        private fun configuredPostfixes(caseInsensitive: Boolean, vararg postfixes: PostfixSetting): SettingsState {
            val setting = SettingsState()
            setting.caseInsensitiveMatching = caseInsensitive
            setting.postfixes.clear()
            setting.postfixes.addAll(postfixes)
            return setting
        }

        private fun createPostfixSetting(pattern: String) = PostfixSetting(pattern, "", "")
    }
}
