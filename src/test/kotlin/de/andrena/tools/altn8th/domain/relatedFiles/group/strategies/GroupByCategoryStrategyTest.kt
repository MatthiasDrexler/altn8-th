package de.andrena.tools.altn8th.domain.relatedFiles.group.strategies

import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationGroup
import de.andrena.tools.altn8th.domain.relatedFiles.RelationType
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.containsExactly

@RunWith(Enclosed::class)
class GroupByCategoryStrategyTest {
    class Group {
        @Test
        fun `should put relations with same category in same group`() {
            val relations = listOf(
                RelationTypeA(File.from("/first/file.ts"), File.from("/first/related/file.ts")),
                RelationTypeA(File.from("/second/file.ts"), File.from("/second/related/file.ts"))
            )

            val result = GroupByCategoryStrategy().group(relations)

            expectThat(result) {
                containsExactly(
                    RelationGroup("RelationTypeA", listOf(relations[0], relations[1]))
                )
            }
        }

        @Test
        fun `should put relations with different categories in separate groups`() {
            val relations = listOf(
                RelationTypeA(File.from("/first/file.ts"), File.from("/first/related/file.ts")),
                RelationTypeB(File.from("/second/file.ts"), File.from("/second/related/file.ts"))
            )

            val result = GroupByCategoryStrategy().group(relations)

            expectThat(result) {
                containsExactly(
                    RelationGroup("RelationTypeA", listOf(relations[0])),
                    RelationGroup("RelationTypeB", listOf(relations[1]))
                )
            }
        }
    }
}

private class RelationTypeA(
    override val relatedFile: File,
    override val origin: File,
    override val explanation: String = "Relation type A",
    override val category: String = "RelationTypeA"
) : Relation {
}


private class RelationTypeB(
    override val relatedFile: File,
    override val origin: File,
    override val explanation: String = "Relation type B",
    override val category: String = "RelationTypeB"
) : Relation
