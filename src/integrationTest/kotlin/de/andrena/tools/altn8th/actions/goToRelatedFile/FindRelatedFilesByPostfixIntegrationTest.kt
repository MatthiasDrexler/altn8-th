package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.driver.sdk.invokeAction
import com.intellij.driver.sdk.ui.components.editorTabs
import com.intellij.driver.sdk.ui.components.ideFrame
import com.intellij.driver.sdk.ui.components.searchEverywherePopup
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
    fun `should find related files by postfix`(testInfo: TestInfo) {
        startIDEFor(
            testInfo,
            LocalProjectInfo(Path("./src/integrationTest/resources/projects/postfix/single"))
        ) {
            ideFrame {
                invokeAction("SearchEverywhere")
                searchEverywherePopup {
                    keyboard {
                        enterText("DefaultEntity")
                        enter()
                    }
                }

                keyboard {
                    hotKey(KeyEvent.VK_ALT, KeyEvent.VK_8)
                }

                editorTabs {
                    expectThat(isTabOpened("DefaultService.kt")).isTrue()
                }
            }
        }
    }
}
