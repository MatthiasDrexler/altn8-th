package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class BaseNameTest {
    class RegardingTo {
        @Test
        fun `should only contain original filename when no postfix matches`() {
            val basename = "basename"
            val file = File(listOf("", "home", "username", "${basename}.txt"))
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                PostfixSetting("AnotherUnrelatedPostfix", "no match", "no match"),
                PostfixSetting("YetAnotherUnrelatedPostfix", "no match", "no match")
            )

            val result = BaseName(file).regardingTo(postfixes)

            expectThat(result) {
                containsKey(basename)
                getValue(basename) isEqualTo null
                hasSize(1)
            }
        }

        @Test
        fun `should contain base filename when postfix matches`() {
            val postfix = "Test"
            val matchingPostfixSetting = PostfixSetting(postfix, "match", "match")
            val basename = "basename"
            val file = File(listOf("", "home", "username", "${basename}${postfix}.txt"))

            val result = BaseName(file).regardingTo(
                listOf(
                    PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                    matchingPostfixSetting
                )
            )

            expectThat(result) {
                containsKey(basename)
                getValue(basename) isEqualTo (PostfixSetting(postfix, "match", "match"))

                containsKey(file.nameWithoutFileExtension())
                getValue(file.nameWithoutFileExtension()) isEqualTo null

                hasSize(2)
            }
        }

        @Test
        fun `should contain only truncate postfix match once`() {
            val postfix = "Test"
            val matchingPostfixSetting = PostfixSetting(postfix, "match", "match")
            val root = "root"
            val file = File(listOf("", "home", "username", "${root}${postfix}${postfix}.txt"))

            val result = BaseName(file)
                .regardingTo(
                    listOf(
                        PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                        matchingPostfixSetting
                    )
                )

            expectThat(result) {
                containsKey("${root}${postfix}")
                getValue("${root}${postfix}") isEqualTo matchingPostfixSetting

                containsKey(file.nameWithoutFileExtension())
                getValue(file.nameWithoutFileExtension()) isEqualTo null

                doesNotContainKey(root)
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

            val settingOfMoreAccurateMatch = PostfixSetting(moreAccurateMatch, "match", "match")
            val settingsOfLessAccurateMatch = PostfixSetting(lessAccurateMatch, "match", "match")

            val result = BaseName(file).regardingTo(
                listOf(
                    PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                    settingOfMoreAccurateMatch,
                    settingsOfLessAccurateMatch
                )
            )

            expectThat(result) {
                containsKey(basename)
                getValue(basename) isEqualTo settingOfMoreAccurateMatch

                containsKey("${basename}${additionalPartOfMoreAccurateMatch}")
                getValue("${basename}${additionalPartOfMoreAccurateMatch}") isEqualTo settingsOfLessAccurateMatch

                containsKey(file.nameWithoutFileExtension())
                getValue(file.nameWithoutFileExtension()) isEqualTo null

                hasSize(3)
            }
        }
    }
}
