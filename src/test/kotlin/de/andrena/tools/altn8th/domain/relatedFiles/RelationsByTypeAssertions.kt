package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File
import strikt.api.Assertion
import strikt.assertions.all
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.isContainedIn
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

internal fun Assertion.Builder<RelationsByType>.originIsRelatedTo(vararg expectedRelatedFiles: File): Assertion.Builder<RelationsByType> =
    compose("is related to") {
        expectedRelatedFiles.forEach {
            get { relatedFiles }.contains(it)
        }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsOnlyRelatedTo(vararg expectedRelatedFiles: File): Assertion.Builder<RelationsByType> =
    compose("origin is only related to") {
        expectedRelatedFiles.forEach {
            get { relatedFiles }.contains(it)
        }
        get { relatedFiles }.all { isContainedIn(expectedRelatedFiles.asIterable()) }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsUnrelatedTo(vararg expectedUnrelatedFiles: File): Assertion.Builder<RelationsByType> =
    compose("origin is unrelated to") {
        get { relatedFiles }.doesNotContain(expectedUnrelatedFiles)
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsUnrelatedToAnyFile(): Assertion.Builder<RelationsByType> =
    compose("origin is not related to any file") {
        get { relatedFiles }.isEmpty()
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsRelatedBy(expectedRelationType: RelationType): Assertion.Builder<RelationsByType> =
    compose("has relation type $expectedRelationType") {
        get { expectedRelationType.key() }.isEqualTo(expectedRelationType.key())
    } then {
        if (allPassed) pass() else fail()
    }
