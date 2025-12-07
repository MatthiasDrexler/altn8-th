package de.andrena.tools.altn8th.domain

import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo


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

    class RelativePathFrom {
        @Test
        fun `should subtract given base path ending with a slash`() {
            val basePath = "/home/user/project/root/"
            val file = File.from("${basePath}some/subfolder/file.txt")

            val result = file.relativeFrom(basePath)

            expectThat(result).isEqualTo("some/subfolder/file.txt")
        }

        @Test
        fun `should subtract given base path ending with multiple slashes`() {
            val basePath = "/home/user/project/root////////////////////"
            val file = File.from("${basePath}some/subfolder/file.txt")

            val result = file.relativeFrom(basePath)

            expectThat(result).isEqualTo("some/subfolder/file.txt")
        }

        @Test
        fun `should subtract given base path ending without a slash`() {
            val basePath = "/home/user/project/root"
            val file = File.from("${basePath}/some/subfolder/file.txt")

            val result = file.relativeFrom(basePath)

            expectThat(result).isEqualTo("some/subfolder/file.txt")
        }

        @Test
        fun `should return full path if base path is not part of file`() {
            val basePath = "/home/user/project/root"
            val file = File.from("/home/user/Downloads/file.txt")

            val result = file.relativeFrom(basePath)

            expectThat(result).isEqualTo("/home/user/Downloads/file.txt")
        }
    }
}
