package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies.postfix

import de.andrena.tools.altn8th.domain.File
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class FileExtensionsTest {
    class BaseNamesFromPostfixes {
        @Test
        fun `should only contain original filename when no postfix matches`() {
            val basename = "basename"
            val file = File(listOf("", "home", "username", "${basename}.txt"))

            val result = file.baseNamesFromPostfixes(listOf("not", "contained", "postfixes", "and", "base"))

            expectThat(result) {
                all { isEqualTo(basename) }
                hasSize(1)
            }
        }

        @Test
        fun `should contain base filename when postfix matches`() {
            val basename = "basename"
            val postfix = "Test"
            val file = File(listOf("", "home", "username", "${basename}${postfix}.txt"))

            val result = file.baseNamesFromPostfixes(listOf("another", "postfix", "and", postfix))

            expectThat(result) {
                all { startsWith(basename) }
                contains(basename)
                contains("${basename}${postfix}")
                hasSize(2)
            }
        }

        @Test
        fun `should contain only truncate postfix match once`() {
            val basename = "basename"
            val postfix = "Test"
            val file = File(listOf("", "home", "username", "${basename}${postfix}${postfix}.txt"))

            val result = file.baseNamesFromPostfixes(listOf("another", "postfix", "and", postfix))

            expectThat(result) {
                all { startsWith(basename) }
                contains("${basename}${postfix}")
                contains("${basename}${postfix}${postfix}")
                doesNotContain(basename)
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

            val result = file.baseNamesFromPostfixes(listOf(moreAccurateMatch, lessAccurateMatch))

            expectThat(result) {
                all { startsWith(basename) }
                contains(basename)
                contains("${basename}${additionalPartOfMoreAccurateMatch}")
                contains("${basename}${moreAccurateMatch}")
                hasSize(3)
            }
        }
    }
}
