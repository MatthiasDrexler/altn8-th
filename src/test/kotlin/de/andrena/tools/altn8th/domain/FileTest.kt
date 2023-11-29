package de.andrena.tools.altn8th.domain

import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*


@RunWith(Enclosed::class)
class FileTest {
    class From {
        @Test
        fun `should create file from path`() {
            val filePathWithThreeSegments = "/home/username/file.txt"

            val result = File.from(filePathWithThreeSegments)

            expectThat(result).isA<File>()
        }

        @Test
        fun `should not throw away root in path`() {
            val filePathWithThreeSegments = "/home/username/file.txt"

            val result = File.from(filePathWithThreeSegments).path()

            expectThat(result).isEqualTo(filePathWithThreeSegments)
        }
    }

    class FileNameWithFileExtension {
        @Test
        fun `should extract filename including file extension`() {
            val fileNameWithExtension = "file.txt"
            val file = File(listOf("home", "username", fileNameWithExtension))

            val result = file.nameWithFileExtension()

            expectThat(result).isEqualTo(fileNameWithExtension)
        }
    }

    class FileNameWithoutFileExtension {
        @Test
        fun `should extract filename excluding file extension`() {
            val fileNameWithoutExtension = "file"
            val file = File(listOf("home", "username", "$fileNameWithoutExtension.txt"))

            val result = file.nameWithoutFileExtension()

            expectThat(result).isEqualTo(fileNameWithoutExtension)
        }
    }

    class FileExtension {
        @Test
        fun `should extract file extension`() {
            val fileExtension = "txt"
            val file = File(listOf("home", "username", "filename.${fileExtension}"))

            val result = file.fileExtension()

            expectThat(result).isEqualTo(fileExtension)
        }
    }

    class Path {
        @Test
        fun `should concatenate to path`() {
            val file = File(listOf("home", "username", "file.txt"))

            val result = file.path()

            expectThat(result).isEqualTo("home/username/file.txt")
        }

        @Test
        fun `should concatenate to path from root`() {
            val file = File(listOf("", "home", "username", "file.txt"))

            val result = file.path()

            expectThat(result).isEqualTo("/home/username/file.txt")
        }
    }

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