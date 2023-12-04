package de.andrena.tools.altn8th.actions.openRelatedFile.operations

import com.intellij.psi.PsiFile
import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class SelectedRelatedFileTest {
    class Open {
        @Test
        fun `should open given file`() {
            val fileToOpen = mockk<PsiFile>()
            justRun { fileToOpen.navigate(any()) }

            SelectedRelatedFile(fileToOpen).open()

            verify(exactly = 1) { fileToOpen.navigate(true) }
            confirmVerified(fileToOpen)
        }
    }
}