package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies.AllAreRelatedType
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies.AllRelatedStrategy
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.strategies.NoneRelatedStrategy
import de.andrena.tools.altn8th.domain.File
import de.andrena.tools.altn8th.domain.relatedFiles.Relation
import de.andrena.tools.altn8th.domain.relatedFiles.RelationsByStrategy
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@RunWith(Enclosed::class)
class AnyRelationsTest {
    class AreFound {
        @Test
        fun `should be false when all relations by type are empty`() {
            val noRelations = RelationsByStrategy(NoneRelatedStrategy(), listOf())

            val result = AnyRelations(listOf(noRelations, noRelations)).areFound()

            expectThat(result).isFalse()
        }

        @Test
        fun `should be true when any relation by type contain relations`() {
            val noRelations = RelationsByStrategy(NoneRelatedStrategy(), listOf())
            val relations = RelationsByStrategy(
                AllRelatedStrategy(),
                listOf(
                    Relation(
                        File.from("/is/related/file.txt"),
                        File.from("is/origin/file.txt"),
                        AllAreRelatedType()
                    )
                )
            )

            val result = AnyRelations(listOf(relations, noRelations)).areFound()

            expectThat(result).isTrue()
        }

        @Test
        fun `should be true when all relations by type contain relations`() {
            val relations = RelationsByStrategy(
                AllRelatedStrategy(),
                listOf(
                    Relation(
                        File.from("/is/related/file.txt"),
                        File.from("is/origin/file.txt"),
                        AllAreRelatedType()
                    )
                )
            )

            val result = AnyRelations(listOf(relations, relations)).areFound()

            expectThat(result).isTrue()
        }
    }
}
