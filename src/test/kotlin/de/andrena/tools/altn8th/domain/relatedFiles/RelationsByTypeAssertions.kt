package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File
import strikt.api.Assertion
import strikt.assertions.*

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
        expectedRelatedFiles.forEach { expectedFile ->
            get { relatedFiles.map { it.relatedFile } }.contains(expectedFile)
        }
        get { relatedFiles.map { it.relatedFile } }.all { isContainedIn(expectedRelatedFiles.asIterable()) }
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
        get { expectedRelationType.name() }.isEqualTo(expectedRelationType.name())
    } then {
        if (allPassed) pass() else fail()
    }
