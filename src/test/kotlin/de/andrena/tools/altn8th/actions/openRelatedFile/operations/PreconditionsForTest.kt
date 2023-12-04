package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.openapi.actionSystem.AnActionEvent
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.SatisfiedPrecondition
import de.andrena.tools.altn8th.actions.openRelatedFile.operations.preconditions.UnsatisfiedPrecondition
import io.mockk.mockk
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@RunWith(Enclosed::class)
class PreconditionsForTest {
    class AreSatisfied {
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