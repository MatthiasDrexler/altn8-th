package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File
import strikt.api.Assertion
import strikt.assertions.*

internal fun Assertion.Builder<Collection<Relation>>.originIsRelatedTo(vararg expectedRelatedFiles: File): Assertion.Builder<Collection<Relation>> =
    compose("is related to") { relations ->
        expectedRelatedFiles.forEach { expected ->
            relations.any { it.relatedFile == expected }
        }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<Collection<Relation>>.originIsOnlyRelatedTo(vararg expectedRelatedFiles: File): Assertion.Builder<Collection<Relation>> =
    compose("origin is only related to") { relations ->
        expectedRelatedFiles.forEach { expected ->
            relations.any { it.relatedFile == expected }
        }
        relations.all { expectedRelatedFiles.contains(it.relatedFile) }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<Collection<Relation>>.originIsUnrelatedTo(vararg expectedUnrelatedFiles: File): Assertion.Builder<Collection<Relation>> =
    compose("origin is unrelated to") { relations ->
        expectedUnrelatedFiles.forEach { expected ->
            relations.none { it.relatedFile == expected }
        }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<Collection<Relation>>.originIsUnrelatedToAnyFile(): Assertion.Builder<Collection<Relation>> =
    compose("origin is not related to any file") {
        isEmpty()
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<Collection<Relation>>.originIsRelatedBy(expectedRelationType: RelationType): Assertion.Builder<Collection<Relation>> =
    compose("has relation type $expectedRelationType") { relations ->
        relations.any { it.type ==  expectedRelationType}
    } then {
        if (allPassed) pass() else fail()
    }
