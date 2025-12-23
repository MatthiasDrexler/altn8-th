package de.andrena.tools.altn8th.actions.goToRelatedFile.operations

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.PreconditionsFor
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.SatisfiedPrecondition
import de.andrena.tools.altn8th.actions.goToRelatedFile.operations.preconditions.UnsatisfiedPrecondition
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class PreconditionsForTest {
    @Nested
    inner class AreSatisfied {
        @Test
        fun `should be true when all preconditions are satisfied`(){
            val actionEvent = mockk<AnActionEvent>()
            val satisfiedPreconditions = listOf(SatisfiedPrecondition(), SatisfiedPrecondition())

            val result = PreconditionsFor(actionEvent, satisfiedPreconditions).areSatisfied()

            expectThat(result).isTrue()
        }

        @Test
        fun `should be false when one precondition is unsatisfied`() {
            val actionEvent = mockk<AnActionEvent>()
            val preconditions = listOf(SatisfiedPrecondition(), UnsatisfiedPrecondition())

            val result = PreconditionsFor(actionEvent, preconditions).areSatisfied()

            expectThat(result).isFalse()
        }

        @Test
        fun `should be false when all preconditions are unsatisfied`() {
            val actionEvent = mockk<AnActionEvent>()
            val unsatisfiedPreconditions = listOf(UnsatisfiedPrecondition(), UnsatisfiedPrecondition())

            val result = PreconditionsFor(actionEvent, unsatisfiedPreconditions).areSatisfied()

            expectThat(result).isFalse()
        }
    }
}
