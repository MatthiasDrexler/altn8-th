package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelationType
import de.andrena.tools.altn8th.settings.SettingsState
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class FindRelatedFilesByFileExtensionStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files with same filename and different not excluded file extension`() {
            val baseNameOfOrigin = "file"
            val allowedFileExtension = "ts"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.txt")
            val allFiles = listOf(
                File.from("/is/related/${baseNameOfOrigin}.${allowedFileExtension}")
            )

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                origin,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.${allowedFileExtension}") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.FILE_EXTENSION) }
                hasSize(1)
            }
        }

        @Test
        fun `should relate files with same name and same not excluded file extension but different path`() {
            val fullFilenameOfOrigin = "file.ts"
            val origin = File.from("/is/origin/$fullFilenameOfOrigin")
            val allFiles = listOf(
                File.from("/is/related/$fullFilenameOfOrigin")
            )

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                origin,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo(fullFilenameOfOrigin) }
                all { get { file.nameWithoutFileExtension() }.startsWith("file") }
                all { get { findingType }.isEqualTo(RelationType.FILE_EXTENSION) }
                hasSize(1)
            }
        }

        @Test
        fun `should not relate files with excluded file extension`() {
            val baseNameOfOrigin = "file"
            val allowedFileExtension = "ts"
            val excludedFileExtension = "js"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.txt")
            val allFiles = listOf(
                File.from("/is/unrelated/${baseNameOfOrigin}.${allowedFileExtension}"),
                File.from("/is/unrelated/${baseNameOfOrigin}.${excludedFileExtension}")
            )

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                origin,
                allFiles,
                excludedFileExtensions(excludedFileExtension)
            )

            expectThat(result) {
                none { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.${excludedFileExtension}") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.${allowedFileExtension}") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.FILE_EXTENSION) }
                hasSize(1)
            }
        }

        @Test
        fun `should not relate equal file`() {
            val origin = File.from("/is/origin/file.txt")
            val allFiles = listOf(
                File.from("/is/origin/file.txt")
            )

            val result = FindRelatedFilesByFileExtensionStrategy().findRelatedFiles(
                origin,
                allFiles,
                excludedFileExtensions()
            )

            expectThat(result) {
                isEmpty()
            }
        }

        private fun excludedFileExtensions(vararg fileExtensions: String): SettingsState {
            val settings = SettingsState()
            settings.excludedFileExtensions.addAll(fileExtensions)
            return settings
        }
    }
}