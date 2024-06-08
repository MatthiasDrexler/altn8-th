package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.prefix

import de.andrena.tools.altn8th.domain.settings.types.PrefixSetting
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.doesNotContainKey
import strikt.assertions.getValue
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@RunWith(Enclosed::class)
class DeprefixerTest {
    class RegardingTo {
        @Test
        fun `should only contain original filename when no prefix matches`() {
            val prefixes = listOf(
                PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                PrefixSetting("AnotherUnrelatedPrefix", "no match", "no match"),
                PrefixSetting("YetAnotherUnrelatedPrefix", "no match", "no match")
            )

            val result = Deprefixer("Basename").regardingTo(prefixes)

            expectThat(result) {
                getValue("Basename") isEqualTo null
                hasSize(1)
            }
        }

        @Test
        fun `should contain base filename when prefix matches`() {
            val matchingPrefixSetting = PrefixSetting("Prefixed", "match", "match")

            val result = Deprefixer("PrefixedBasename").regardingTo(
                listOf(
                    PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                    matchingPrefixSetting
                )
            )

            expectThat(result) {
                getValue("Basename") isEqualTo matchingPrefixSetting
                getValue("PrefixedBasename") isEqualTo null
                hasSize(2)
            }
        }

        @Test
        fun `should truncate prefix match only once`() {
            val matchingPrefixSetting = PrefixSetting("Prefixed", "match", "match")

            val result = Deprefixer("PrefixedPrefixedBasename")
                .regardingTo(
                    listOf(
                        PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                        matchingPrefixSetting
                    )
                )

            expectThat(result) {
                getValue("PrefixedBasename") isEqualTo matchingPrefixSetting
                getValue("PrefixedPrefixedBasename") isEqualTo null
                doesNotContainKey("Basename")
                hasSize(2)
            }
        }

        @Test
        fun `should contain multiple bases if multiple prefixes match`() {
            val settingOfMoreAccurateMatch = PrefixSetting("PrefixedLonger", "match", "match")
            val settingsOfLessAccurateMatch = PrefixSetting("Prefixed", "match", "match")

            val result = Deprefixer("PrefixedLongerBasename").regardingTo(
                listOf(
                    PrefixSetting("UnrelatedPrefix", "no match", "no match"),
                    settingOfMoreAccurateMatch,
                    settingsOfLessAccurateMatch
                )
            )

            expectThat(result) {
                getValue("Basename") isEqualTo settingOfMoreAccurateMatch
                getValue("LongerBasename") isEqualTo settingsOfLessAccurateMatch
                getValue("PrefixedLongerBasename") isEqualTo null
                hasSize(3)
            }
        }
    }
}
