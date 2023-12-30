package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File
import strikt.api.Assertion
import strikt.assertions.*

internal fun Assertion.Builder<RelationsByStrategy>.originIsRelatedTo(vararg expectedRelatedFiles: File): Assertion.Builder<RelationsByStrategy> =
    compose("is related to") {
        expectedRelatedFiles.forEach {
            get { relations }.contains(it)
        }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByStrategy>.originIsOnlyRelatedTo(vararg expectedRelatedFiles: File): Assertion.Builder<RelationsByStrategy> =
    compose("origin is only related to") {
        expectedRelatedFiles.forEach { expectedFile ->
            get { relations.map { it.relatedFile } }.contains(expectedFile)
        }
        get { relations.map { it.relatedFile } }.all { isContainedIn(expectedRelatedFiles.asIterable()) }
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByStrategy>.originIsUnrelatedTo(vararg expectedUnrelatedFiles: File): Assertion.Builder<RelationsByStrategy> =
    compose("origin is unrelated to") {
        get { relations }.doesNotContain(expectedUnrelatedFiles)
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByStrategy>.originIsUnrelatedToAnyFile(): Assertion.Builder<RelationsByStrategy> =
    compose("origin is not related to any file") {
        get { relations }.isEmpty()
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByStrategy>.originIsRelatedBy(expectedRelationType: RelationType): Assertion.Builder<RelationsByStrategy> =
    compose("has relation type $expectedRelationType") {
        get { expectedRelationType.name() }.isEqualTo(expectedRelationType.name())
    } then {
        if (allPassed) pass() else fail()
    }
