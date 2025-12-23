package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import strikt.api.expectThat
import strikt.assertions.doesNotContainKey
import strikt.assertions.getValue
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class DepostfixerTest {
    @Nested
    inner class RegardingTo {
        @Test
        fun `should only contain original filename when no postfix matches`() {
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                PostfixSetting("AnotherUnrelatedPostfix", "no match", "no match"),
                PostfixSetting("YetAnotherUnrelatedPostfix", "no match", "no match")
            )

            val result = Depostfixer("Basename").regardingTo(postfixes)

            expectThat(result) {
                getValue("Basename") isEqualTo null
                hasSize(1)
            }
        }

        @Test
        fun `should contain base filename when postfix matches`() {
            val matchingPostfixSetting = PostfixSetting("Postfix", "match", "match")

            val result = Depostfixer("BasenamePostfix").regardingTo(
                listOf(
                    PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                    matchingPostfixSetting
                )
            )

            expectThat(result) {
                getValue("Basename") isEqualTo matchingPostfixSetting
                getValue("BasenamePostfix") isEqualTo null
                hasSize(2)
            }
        }

        @Test
        fun `should truncate postfix match only once`() {
            val matchingPostfixSetting = PostfixSetting("Postfix", "match", "match")

            val result = Depostfixer("BasenamePostfixPostfix")
                .regardingTo(
                    listOf(
                        PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                        matchingPostfixSetting
                    )
                )

            expectThat(result) {
                getValue("BasenamePostfix") isEqualTo matchingPostfixSetting
                getValue("BasenamePostfixPostfix") isEqualTo null
                doesNotContainKey("Basename")
                hasSize(2)
            }
        }

        @Test
        fun `should contain multiple bases if multiple postfixes match`() {
            val settingOfMoreAccurateMatch = PostfixSetting("LongerPostfix", "match", "match")
            val settingsOfLessAccurateMatch = PostfixSetting("Postfix", "match", "match")

            val result = Depostfixer("BasenameLongerPostfix").regardingTo(
                listOf(
                    PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                    settingOfMoreAccurateMatch,
                    settingsOfLessAccurateMatch
                )
            )

            expectThat(result) {
                getValue("Basename") isEqualTo settingOfMoreAccurateMatch
                getValue("BasenameLonger") isEqualTo settingsOfLessAccurateMatch
                getValue("BasenameLongerPostfix") isEqualTo null
                hasSize(3)
            }
        }
    }
}
