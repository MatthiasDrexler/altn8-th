package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class BaseNameTest {
    class RegardingTo {
        @Test
        fun `should only contain original filename when no prefix matches`() {
            val basename = "Basename"
            val file = File(listOf("", "home", "username", "${basename}.txt"))
            val prefixes = listOf(
                PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                PrefixSetting("AnotherUnrelatedPrefix", "no match", "no match"),
                PrefixSetting("YetAnotherUnrelatedPrefix", "no match", "no match")
            )

            val result = BaseName(file).regardingTo(prefixes)

            expectThat(result) {
                containsKey(basename)
                getValue(basename) isEqualTo null
                hasSize(1)
            }
        }

        @Test
        fun `should contain base filename when prefix matches`() {
            val prefix = "Test"
            val matchingPrefixSetting = PrefixSetting(prefix, "match", "match")
            val basename = "Basename"
            val file = File(listOf("", "home", "username", "${prefix}${basename}.txt"))

            val result = BaseName(file).regardingTo(
                listOf(
                    PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                    matchingPrefixSetting
                )
            )

            expectThat(result) {
                containsKey(basename)
                getValue(basename) isEqualTo (PrefixSetting(prefix, "match", "match"))

                containsKey(file.nameWithoutFileExtension())
                getValue(file.nameWithoutFileExtension()) isEqualTo null

                hasSize(2)
            }
        }

        @Test
        fun `should contain only truncate prefix match once`() {
            val prefix = "Test"
            val matchingPrefixSetting = PrefixSetting(prefix, "match", "match")
            val root = "Root"
            val file = File(listOf("", "home", "username", "${prefix}${prefix}${root}.txt"))

            val result = BaseName(file)
                .regardingTo(
                    listOf(
                        PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                        matchingPrefixSetting
                    )
                )

            expectThat(result) {
                containsKey("${prefix}${root}")
                getValue("${prefix}${root}") isEqualTo matchingPrefixSetting

                containsKey(file.nameWithoutFileExtension())
                getValue(file.nameWithoutFileExtension()) isEqualTo null

                doesNotContainKey(root)
                hasSize(2)
            }
        }

        @Test
        fun `should contain multiple bases if multiple prefixes match`() {
            val lessAccurateMatch = "Test"
            val additionalPartOfMoreAccurateMatch = "All"
            val basename = "Basename"
            val moreAccurateMatch = "${lessAccurateMatch}${additionalPartOfMoreAccurateMatch}"
            val file = File(listOf("", "home", "username", "${moreAccurateMatch}${basename}.txt"))

            val settingOfMoreAccurateMatch = PrefixSetting(moreAccurateMatch, "match", "match")
            val settingsOfLessAccurateMatch = PrefixSetting(lessAccurateMatch, "match", "match")

            val result = BaseName(file).regardingTo(
                listOf(
                    PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                    settingOfMoreAccurateMatch,
                    settingsOfLessAccurateMatch
                )
            )

            expectThat(result) {
                containsKey(basename)
                getValue(basename) isEqualTo settingOfMoreAccurateMatch

                containsKey("${additionalPartOfMoreAccurateMatch}${basename}")
                getValue("${additionalPartOfMoreAccurateMatch}${basename}") isEqualTo settingsOfLessAccurateMatch

                containsKey(file.nameWithoutFileExtension())
                getValue(file.nameWithoutFileExtension()) isEqualTo null

                hasSize(3)
            }
        }
    }
}
