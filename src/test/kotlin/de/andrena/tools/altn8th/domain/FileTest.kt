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
            val file = File(listOf("home", "username", "${fileNameWithoutExtension}.txt"))

            val result = file.nameWithoutFileExtension()

            expectThat(result).isEqualTo(fileNameWithoutExtension)
        }

        @Test
        fun `should extract filename excluding file extension for hidden files`() {
            val dotFile = File(listOf("home", "username", ".hidden.txt"))

            val result = dotFile.nameWithoutFileExtension()

            expectThat(result).isEqualTo(".hidden")
        }

        @Test
        fun `should extract filename excluding file extension for hidden files with multiple dots`() {
            val dotFile = File(listOf("home", "username", "...hidden.txt"))

            val result = dotFile.nameWithoutFileExtension()

            expectThat(result).isEqualTo("...hidden")
        }

        @Test
        fun `should not extract parts of filename for dot-files like env`() {
            val dotFile = File(listOf("home", "username", ".env"))

            val result = dotFile.nameWithoutFileExtension()

            expectThat(result).isEqualTo(".env")
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

        @Test
        fun `should extract file extension for dot-files like env`() {
            val file = File(listOf("home", "username", ".env"))

            val result = file.fileExtension()

            expectThat(result).isEqualTo("env")
        }

        @Test
        fun `should extract file extension for dot-files like env with multiple dots`() {
            val file = File(listOf("home", "username", "...env"))

            val result = file.fileExtension()

            expectThat(result).isEqualTo("env")
        }

        @Test
        fun `should extract empty file extension files ending with dot`() {
            val file = File(listOf("home", "username", ".hidden."))

            val result = file.fileExtension()

            expectThat(result).isEqualTo("")
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