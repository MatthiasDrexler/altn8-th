package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies.AllRelatedStrategy
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies.NoneRelatedStrategy
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.settings.SettingsState
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.*

@RunWith(Enclosed::class)
class RelatedFilesWithinTest {
    class Find {
        @Test
        fun `should find related files when all files are related`() {
            val origin = File.from("/is/origin/file.txt")
            val projectFile = File.from("/is/project/file1.txt")
            val anotherProjectFile = File.from("/is/project/file2.txt")
            val strategies = listOf(AllRelatedStrategy(), AllRelatedStrategy())

            val result = RelatedFilesWithin(
                origin,
                listOf(origin, projectFile, anotherProjectFile),
                SettingsState(),
                strategies
            ).find()

            expectThat(result) {
                all { get { relatedFiles }.containsExactlyInAnyOrder(projectFile, anotherProjectFile) }
                doesNotContain(origin)
            }
        }

        @Test
        fun `should not find related files when all files are unrelated`() {
            val origin = File.from("/is/origin/file.txt")
            val projectFile = File.from("/is/project/file1.txt")
            val anotherProjectFile = File.from("/is/project/file2.txt")
            val strategies = listOf(NoneRelatedStrategy(), NoneRelatedStrategy())

            val result = RelatedFilesWithin(
                origin,
                listOf(origin, projectFile, anotherProjectFile),
                SettingsState(),
                strategies
            ).find()

            expectThat(result) {
                all { get { relatedFiles }.isEmpty() }
                doesNotContain(origin)
            }
        }

        @Test
        fun `should find related files when some files are related`() {
            val origin = File.from("/is/origin/file.txt")
            val projectFile = File.from("/is/project/file1.txt")
            val anotherProjectFile = File.from("/is/project/file2.txt")
            val strategies = listOf(NoneRelatedStrategy(), AllRelatedStrategy())

            val result = RelatedFilesWithin(
                origin,
                listOf(origin, projectFile, anotherProjectFile),
                SettingsState(),
                strategies
            ).find()

            expectThat(result) {
                one { get { relatedFiles }.containsExactlyInAnyOrder(projectFile, anotherProjectFile) }
                one { get { relatedFiles }.isEmpty() }
                doesNotContain(origin)
            }
        }
    }
}
