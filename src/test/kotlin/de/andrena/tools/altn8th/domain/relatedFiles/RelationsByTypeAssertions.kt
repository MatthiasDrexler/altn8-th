package de.andrena.tools.altn8th.domain.relatedFiles

import de.andrena.tools.altn8th.domain.File
import strikt.api.Assertion
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

internal fun Assertion.Builder<RelationsByType>.originIsRelatedTo(file: File): Assertion.Builder<RelationsByType> =
    compose("is related to") {
        get { relatedFiles }.contains(file)
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsNotRelatedTo(file: File): Assertion.Builder<RelationsByType> =
    compose("is not related to") {
        get { relatedFiles }.doesNotContain(file)
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsNotRelatedToAnyFile(): Assertion.Builder<RelationsByType> =
    compose("is not related to") {
        get { relatedFiles }.isEmpty()
    } then {
        if (allPassed) pass() else fail()
    }

internal fun Assertion.Builder<RelationsByType>.originIsRelatedBy(type: RelationType): Assertion.Builder<RelationsByType> =
    compose("is related by") {
        get { type.key() }.isEqualTo(type.key())
    } then {
        if (allPassed) pass() else fail()
    }
