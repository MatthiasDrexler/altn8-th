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
                Relation(File.from("/first/file.ts"), File.from("/first/related/file.ts"), RelationTypeA()),
                Relation(File.from("/second/file.ts"), File.from("/second/related/file.ts"), RelationTypeA())
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
                Relation(File.from("/first/file.ts"), File.from("/first/related/file.ts"), RelationTypeA()),
                Relation(File.from("/second/file.ts"), File.from("/second/related/file.ts"), RelationTypeB())
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

private class RelationTypeA : RelationType {
    override fun name() = "RelationTypeA"
    override fun explanation() = "Relation type A"
    override fun category() = "RelationTypeA"
}


private class RelationTypeB : RelationType {
    override fun name() = "RelationTypeB"
    override fun explanation() = "Relation type B"
    override fun category() = "RelationTypeB"
}
