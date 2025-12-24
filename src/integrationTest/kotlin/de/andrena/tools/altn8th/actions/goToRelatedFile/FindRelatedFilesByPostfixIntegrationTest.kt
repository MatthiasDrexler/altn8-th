package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.driver.sdk.openFile
import com.intellij.driver.sdk.ui.components.editorTabs
import com.intellij.driver.sdk.ui.components.ideFrame
import com.intellij.ide.starter.project.LocalProjectInfo
import de.andrena.tools.altn8th.startIDEFor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import strikt.api.expectThat
import strikt.assertions.isTrue
import java.awt.event.KeyEvent
import kotlin.io.path.Path

class FindRelatedFilesByPostfixIntegrationTest {

    @Test
    fun `should find single related file by postfix`(testInfo: TestInfo) {
        startIDEFor(
            testInfo,
            LocalProjectInfo(Path("./src/integrationTest/resources/projects/postfix/single"))
        ) {
            ideFrame {
                openFile("src/SingleEntity.kt")

                keyboard {
                    hotKey(KeyEvent.VK_ALT, KeyEvent.VK_8)
                }

                editorTabs {
                    expectThat(isTabOpened("SingleService.kt")).isTrue()
                }
            }
        }
    }
}
