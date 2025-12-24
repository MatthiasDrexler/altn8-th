package de.andrena.tools.altn8th.actions.goToRelatedFile

import com.intellij.driver.sdk.openFile
import com.intellij.driver.sdk.ui.components.editorTabs
import com.intellij.driver.sdk.ui.components.ideFrame
import com.intellij.ide.starter.project.LocalProjectInfo
import de.andrena.tools.altn8th.selectRelatedFile
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

    @Test
    fun `should find multiple related files by postfix`(testInfo: TestInfo) {
        startIDEFor(
            testInfo,
            LocalProjectInfo(Path("./src/integrationTest/resources/projects/postfix/multiple"))
        ) {
            ideFrame {
                openFile("src/MultipleEntity.kt")

                keyboard {
                    hotKey(KeyEvent.VK_ALT, KeyEvent.VK_8)
                }

                selectRelatedFile("MultipleService")

                editorTabs {
                    expectThat(isTabOpened("MultipleService.kt")).isTrue()
                }
            }
        }
    }

    @Test
    fun `should open multiple related files by category`(testInfo: TestInfo) {
        startIDEFor(
            testInfo,
            LocalProjectInfo(Path("./src/integrationTest/resources/projects/postfix/multiple"))
        ) {
            ideFrame {
                openFile("src/MultipleEntity.kt")

                keyboard {
                    hotKey(KeyEvent.VK_ALT, KeyEvent.VK_8)
                }

                selectRelatedFile("Testing ↲")

                editorTabs {
                    expectThat(isTabOpened("MultipleTest.kt")).isTrue()
                    expectThat(isTabOpened("MultipleTests.kt")).isTrue()
                    expectThat(isTabOpened("MultipleIntegrationTest.kt")).isTrue()
                    expectThat(isTabOpened("MultipleIntegrationTests.kt")).isTrue()
                }
            }
        }
    }
}
