package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.settings.types.PostfixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class DepostfixerTest {
    class RegardingTo {
        @Test
        fun `should only contain original filename when no postfix matches`() {
            val postfixes = listOf(
                PostfixSetting("UnrelatedPostfix", "no match", "no match"),
                PostfixSetting("AnotherUnrelatedPostfix", "no match", "no match"),
                PostfixSetting("YetAnotherUnrelatedPostfix", "no match", "no match")
            )

            val result = Depostfixer("Basename").regardingTo(postfixes)

            expectThat(result) {
                containsKey("Basename")
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
                containsKey("Basename")
                getValue("Basename") isEqualTo matchingPostfixSetting

                containsKey("BasenamePostfix")
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
                containsKey("BasenamePostfix")
                getValue("BasenamePostfix") isEqualTo matchingPostfixSetting

                containsKey("BasenamePostfixPostfix")
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
                containsKey("Basename")
                getValue("Basename") isEqualTo settingOfMoreAccurateMatch

                containsKey("BasenameLonger")
                getValue("BasenameLonger") isEqualTo settingsOfLessAccurateMatch

                containsKey("BasenameLongerPostfix")
                getValue("BasenameLongerPostfix") isEqualTo null

                hasSize(3)
            }
        }
    }
}
