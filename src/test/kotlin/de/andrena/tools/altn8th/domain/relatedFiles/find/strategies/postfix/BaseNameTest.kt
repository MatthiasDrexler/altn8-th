package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.containsKey
import strikt.assertions.doesNotContainKey
import strikt.assertions.hasSize

@RunWith(Enclosed::class)
class BaseNameTest {
    class RegardingTo {
        @Test
        fun `should only contain original filename when no postfix matches`() {
            val basename = "basename"
            val file = File(listOf("", "home", "username", "${basename}.txt"))
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match"),
                PostfixSetting("AnotherUnrelatedPostfix", "no match"),
                PostfixSetting("YetAnotherUnrelatedPostfix", "no match")
            )

            val result = BaseName(file).regardingTo(postfixes)

            expectThat(result) {
                containsKey(basename)
                hasSize(1)
            }
        }

        @Test
        fun `should contain base filename when postfix matches`() {
            val basename = "basename"
            val postfix = "Test"
            val file = File(listOf("", "home", "username", "${basename}${postfix}.txt"))
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match"),
                PostfixSetting(postfix, "match")
            )

            val result = BaseName(file).regardingTo(postfixes)

            expectThat(result) {
                containsKey(basename)
                containsKey("${basename}${postfix}")
                hasSize(2)
            }
        }

        @Test
        fun `should contain only truncate postfix match once`() {
            val basename = "basename"
            val postfix = "Test"
            val file = File(listOf("", "home", "username", "${basename}${postfix}${postfix}.txt"))
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match"),
                PostfixSetting(postfix, "match")
            )

            val result = BaseName(file).regardingTo(postfixes)

            expectThat(result) {
                containsKey("${basename}${postfix}")
                containsKey("${basename}${postfix}${postfix}")
                doesNotContainKey(basename)
                hasSize(2)
            }
        }

        @Test
        fun `should contain multiple bases if multiple postfixes match`() {
            val basename = "basename"
            val additionalPartOfMoreAccurateMatch = "Unit"
            val lessAccurateMatch = "Test"
            val moreAccurateMatch = "${additionalPartOfMoreAccurateMatch}${lessAccurateMatch}"
            val file = File(listOf("", "home", "username", "${basename}${moreAccurateMatch}.txt"))
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match"),
                PostfixSetting(moreAccurateMatch, "match"),
                PostfixSetting(lessAccurateMatch, "match")
            )

            val result = BaseName(file).regardingTo(postfixes)

            expectThat(result) {
                containsKey(basename)
                containsKey("${basename}${additionalPartOfMoreAccurateMatch}")
                containsKey("${basename}${moreAccurateMatch}")
                hasSize(3)
            }
        }
    }
}
