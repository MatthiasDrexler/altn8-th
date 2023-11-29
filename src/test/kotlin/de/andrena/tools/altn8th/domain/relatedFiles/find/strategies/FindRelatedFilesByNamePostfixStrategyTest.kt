package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.SettingsState
import de.andrena.tools.altn8th.domain.relatedFiles.find.RelationType
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class FindRelatedFilesByNamePostfixStrategyTest {
    class FindRelatedFiles {
        @Test
        fun `should relate files with relating postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.kt")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/related/${baseNameOfOrigin}Test.kt"),
                File.from("/is/related/${baseNameOfOrigin}Tests.kt")
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Test.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Tests.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(2)
            }
        }

        @Test
        fun `should relate files with relating regex postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.kt")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/related/${baseNameOfOrigin}Test.kt"),
                File.from("/is/related/${baseNameOfOrigin}Tests.kt")
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Tests?")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Test.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Tests.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(2)
            }
        }

        @Test
        fun `should relate files from files with relating postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}Test.kt")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/related/${baseNameOfOrigin}.kt"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(1)
            }
        }

        @Test
        fun `should relate files from files with relating regex postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}Test.kt")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/related/${baseNameOfOrigin}.kt"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Tests?")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(1)
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating postfixes`() {
            val baseNameOfOrigin = "origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.component.ts")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/origin/${baseNameOfOrigin}.service.ts"),
                File.from("/is/origin/${baseNameOfOrigin}.store.ts"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("\\.component", "\\.service", "\\.store")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.service.ts") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.store.ts") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(2)
            }
        }

        @Test
        fun `should relate files with postfix with other files with relating regex postfixes`() {
            val baseNameOfOrigin = "origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.component.ts")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/origin/${baseNameOfOrigin}.todo-store.ts"),
                File.from("/is/origin/${baseNameOfOrigin}.alarm-store.ts"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("\\.component", "\\.[\\-\\w]*store")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.todo-store.ts") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.alarm-store.ts") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(2)
            }
        }

        @Test
        fun `should relate files with postfix with other files with intersecting relating postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}UnitTest.kt")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/related/${baseNameOfOrigin}.kt"),
                File.from("/is/related/${baseNameOfOrigin}Unit.kt")
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Test", "UnitTest")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Unit.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(2)
            }
        }

        @Test
        fun `should relate files with postfix with other files with intersecting relating regex postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}UnitTest.kt")
            val allFilesContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/related/${baseNameOfOrigin}.kt"),
                File.from("/is/related/${baseNameOfOrigin}Unit.kt")
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesContainingRelatedFiles,
                configuredPostfixes("Tests?", "UnitTests?")
            )

            expectThat(result) {
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Unit.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(2)
            }
        }

        @Test
        fun `should not relate files with some prefix`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                File.from("/is/unrelated/UnrelatedTest.kt"),
                File.from("/is/unrelated/Unrelated${baseNameOfOrigin}Test.kt"),
                File.from("/is/unrelated/Unrelated${baseNameOfOrigin}Tests.kt"),
                File.from("/is/unrelated/Un${baseNameOfOrigin}Test.kt"),
                File.from("/is/unrelated/1${baseNameOfOrigin}Test.kt"),
                File.from("/is/unrelated/_${baseNameOfOrigin}Test.kt"),
                File.from("/is/unrelated/Unrelated${baseNameOfOrigin}${baseNameOfOrigin}Test.kt")
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                isEmpty()
            }
        }

        @Test
        fun `should not relate files without relating postfixes`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                File.from("/is/unrelated/${baseNameOfOrigin}Unrelated.kt"),
                File.from("/is/unrelated/${baseNameOfOrigin}UnrelatedTest.kt")
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                isEmpty()
            }
        }

        @Test
        fun `should not contain origin as related file from base file to file with postfix`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                File.from("/is/origin/${baseNameOfOrigin}.kt"),
                File.from("/is/origin/${baseNameOfOrigin}Test.kt"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                none { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Test.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(1)
            }
        }

        @Test
        fun `should not contain origin as related file from file with postfix to base file`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}Test.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                File.from("/is/origin/${baseNameOfOrigin}.kt"),
                File.from("/is/origin/${baseNameOfOrigin}Test.kt"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                none { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Test.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(1)
            }
        }

        @Test
        fun `should not contain origin as related file from file with postfix to another postfix file`() {
            val baseNameOfOrigin = "Origin"
            val origin = File.from("/is/origin/${baseNameOfOrigin}Test.kt")
            val allFilesNotContainingRelatedFiles = listOf(
                File.from("/is/origin/${baseNameOfOrigin}Tests.kt"),
                File.from("/is/origin/${baseNameOfOrigin}Test.kt"),
            )

            val result = FindRelatedFilesByNamePostfixStrategy().findRelatedFiles(
                origin,
                allFilesNotContainingRelatedFiles,
                configuredPostfixes("Test", "Tests")
            )

            expectThat(result) {
                none { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Test.kt") }
                any { get { file.nameWithFileExtension() }.isEqualTo("${baseNameOfOrigin}Tests.kt") }
                all { get { file.nameWithoutFileExtension() }.startsWith(baseNameOfOrigin) }
                all { get { findingType }.isEqualTo(RelationType.NAME_POSTFIX) }
                hasSize(1)
            }
        }

        private fun configuredPostfixes(vararg postfixes: String): SettingsState {
            val setting = SettingsState()
            setting.postfixes.addAll(postfixes)
            return setting
        }
    }
}
