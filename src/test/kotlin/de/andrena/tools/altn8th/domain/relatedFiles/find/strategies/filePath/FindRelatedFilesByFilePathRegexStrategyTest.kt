package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.filePath

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.SettingsState
import de.andrena.tools.altn8th.domain.settings.types.FilePathRegexSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(Enclosed::class)
class FindRelatedFilesByFilePathRegexStrategyTest {
    class FindRelatedFiles {
        val caseInsensitive = true
        val caseSensitive = false

        @Test
        fun `should relate files by filename relations for fixed destination without escaping`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source",
                "/is/related/destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by filename relations for fixed destination with escaping`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "\\/is\\/origin\\/source",
                "\\/is\\/related\\/destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle file paths with special regex characters`() {
            val originFile = File.from("/is/[origin]/source")
            val relatedFile = File.from("/is/[related]/destination")
            val filenameRegexSetting =
                FilePathRegexSetting("\\/is\\/\\[origin\\]\\/source", "\\/is\\/\\[related\\]\\/destination", "category")

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filenameRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filenameRegexSetting))
            }
        }

        @Test
        fun `should relate files case-insensitively by file path relations for fixed destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/Source",
                "/is/related/Destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseInsensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by file path relations for regex destination`() {
            val originFile = File.from("/is/origin/source")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source",
                "/is/related/destination[\\w]*",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by file path relations for regex origin`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destination")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source[\\w]*",
                "/is/related/destination",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files by file path relations for regex origin and destination`() {
            val originFile = File.from("/is/origin/sourceWithSuffix")
            val relatedFile = File.from("/is/related/destinationWithSuffix")
            val filePathRegexSetting = FilePathRegexSetting(
                "/is/origin/source[\\w]*",
                "/is/related/destination[\\w]*",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should relate files using single named group reference`() {
            val originFile = File.from("/foo/bar/copilot-instructions.md")
            val relatedFile = File.from("/foo/bar/instructions/specific.instructions.md")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<github>.*)/copilot-instructions\\.md",
                "#{github}/instructions/specific\\.instructions\\.md",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should not relate files when named group does not match`() {
            val originFile = File.from("/foo/bar/copilot-instructions.md")
            val relatedFile = File.from("/baz/qux/instructions/specific.instructions.md")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<github>.*)/copilot-instructions\\.md",
                "#{github}/instructions/specific\\.instructions\\.md",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result).isEqualTo(null)
        }

        @Test
        fun `should relate files using multiple named group references`() {
            val originFile = File.from("/project/module/src/main/copilot-instructions.md")
            val relatedFile = File.from("/project/module/instructions/specific.instructions.md")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<project>.*)/(?<module>.*)/src/main/copilot-instructions\\.md",
                "#{project}/#{module}/instructions/specific\\.instructions\\.md",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should reuse same named group multiple times`() {
            val originFile = File.from("/foo/bar/source.txt")
            val relatedFile = File.from("/foo/bar/intermediate/foo/bar/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base>.*)/source\\.txt",
                "#{base}/intermediate#{base}/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle named groups with special regex characters in captured values`() {
            val originFile = File.from("/foo/[test]/source.txt")
            val relatedFile = File.from("/foo/[test]/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base>.*)/source\\.txt",
                "#{base}/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should work with case insensitive matching`() {
            val originFile = File.from("/foo/bar/copilot-instructions.md")
            val relatedFile = File.from("/foo/bar/instructions/specific.instructions.md")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<github>.*)/COPILOT-INSTRUCTIONS\\.MD",
                "#{github}/instructions/SPECIFIC\\.INSTRUCTIONS\\.MD",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseInsensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle escaped hash signs as literal text`() {
            val originFile = File.from("/foo/bar/source.txt")
            val relatedFile = File.from("/foo/bar/#price/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base>.*)/source\\.txt",
                "#{base}/\\#price/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle empty captured groups`() {
            val originFile = File.from("/source.txt")
            val relatedFile = File.from("/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<prefix>.*?)source\\.txt",
                "#{prefix}destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should work without named groups for backward compatibility`() {
            val originFile = File.from("/foo/bar/source.txt")
            val relatedFile = File.from("/foo/bar/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "/foo/bar/source\\.txt",
                "/foo/bar/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should gracefully handle non-existent named group by substituting with empty string`() {
            val originFile = File.from("/foo/bar/source.txt")
            val relatedFile = File.from("/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base>.*)/source\\.txt",
                "#{nonexistent}/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            // The missing group is replaced with empty string, so pattern becomes "/destination\.txt"
            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle complex nested directory structure`() {
            val originFile = File.from("/projects/myapp/src/main/kotlin/com/example/App.kt")
            val relatedFile = File.from("/projects/myapp/src/test/kotlin/com/example/AppTest.kt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<root>.*)/src/main/kotlin/(?<package>.*)/(?<name>.*)\\.kt",
                "#{root}/src/test/kotlin/#{package}/#{name}Test\\.kt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle dollar signs in paths without ambiguity`() {
            val originFile = File.from("/foo/bar/source.txt")
            val relatedFile = File.from("/foo/bar/destination\$123.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base>.*)/source\\.txt",
                "#{base}/destination\\$123\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should handle digits in named capturing group identifier`() {
            val originFile = File.from("/foo/bar/source.txt")
            val relatedFile = File.from("/foo/bar/destination\$123.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base1>.*)/source\\.txt",
                "#{base1}/destination\\$123\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result) {
                isEqualTo(FilePathRegexRelation.from(relatedFile, originFile, filePathRegexSetting))
            }
        }

        @Test
        fun `should ignore underscore in named capturing group identifier`() {
            val originFile = File.from("/foo.bar/baz.qux/source.txt")
            val relatedFile = File.from("/foo.bar/baz.qux/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base_dir>.*)/source\\.txt",
                "#{base_dir}/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result).isEqualTo(null)
        }

        @Test
        fun `should ignore dollar sign in named capturing group identifier`() {
            val originFile = File.from("/foo.bar/baz.qux/source.txt")
            val relatedFile = File.from("/foo.bar/baz.qux/destination.txt")
            val filePathRegexSetting = FilePathRegexSetting(
                "(?<base$>.*)/source\\.txt",
                "#{base$}/destination\\.txt",
                "category"
            )

            val result = FindRelatedFilesByFilePathRegexStrategy().find(
                originFile,
                relatedFile,
                configuredFilePathRegexes(caseSensitive, filePathRegexSetting)
            )

            expectThat(result).isEqualTo(null)
        }

        private fun configuredFilePathRegexes(
            caseInsensitive: Boolean,
            vararg filePathRegexes: FilePathRegexSetting
        ): SettingsState {
            val settings = SettingsState()
            settings.caseInsensitiveMatching = caseInsensitive
            settings.filePathRegexes.clear()
            settings.filePathRegexes.addAll(filePathRegexes)
            return settings
        }
    }
}
