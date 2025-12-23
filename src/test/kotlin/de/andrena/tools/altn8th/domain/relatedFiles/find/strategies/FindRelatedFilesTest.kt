package de.andrena.tools.altn8th.domain.relatedFiles.find.strategies

import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.strategies.AllRelatedStrategy
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.strategies.NoneRelatedStrategy
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.originIsOnlyRelatedTo
import de.andrena.tools.altn8th.domain.relatedFiles.originIsUnrelatedToAnyFile
import de.andrena.tools.altn8th.domain.settings.SettingsState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import strikt.api.expectThat

class FindRelatedFilesTest {
    @Nested
    inner class Find {
        @Test
        fun `should find related files when all files are related`() {
            val origin = File.from("/is/origin/file.txt")
            val projectFile = File.from("/is/project/file1.txt")
            val anotherProjectFile = File.from("/is/project/file2.txt")
            val strategies = listOf(AllRelatedStrategy(), AllRelatedStrategy())

            val result = FindRelatedFiles(
                strategies,
                SettingsState(),
            ).findFor(
                origin,
                listOf(origin, projectFile, anotherProjectFile),
            )

            expectThat(result) {
                originIsOnlyRelatedTo(
                    projectFile,
                    anotherProjectFile
                )
            }
        }

        @Test
        fun `should not find related files when all files are unrelated`() {
            val origin = File.from("/is/origin/file.txt")
            val projectFile = File.from("/is/project/file1.txt")
            val anotherProjectFile = File.from("/is/project/file2.txt")
            val strategies = listOf(NoneRelatedStrategy(), NoneRelatedStrategy())

            val result = FindRelatedFiles(
                strategies,
                SettingsState(),
            ).findFor(
                origin,
                listOf(origin, projectFile, anotherProjectFile),
            )

            expectThat(result) {
                originIsUnrelatedToAnyFile()
            }
        }

        @Test
        fun `should find related files when some files are related`() {
            val origin = File.from("/is/origin/file.txt")
            val projectFile = File.from("/is/project/file1.txt")
            val anotherProjectFile = File.from("/is/project/file2.txt")
            val strategies = listOf(NoneRelatedStrategy(), AllRelatedStrategy())

            val result = FindRelatedFiles(
                strategies,
                SettingsState(),
            ).findFor(
                origin,
                listOf(origin, projectFile, anotherProjectFile),
            )

            expectThat(result) {
                originIsOnlyRelatedTo(
                    projectFile,
                    anotherProjectFile
                )
            }
        }
    }
}
