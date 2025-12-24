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

class OpenFilesByCategoryIntegrationTest {

    @Test
    fun `should not allow to select single item categories`(testInfo: TestInfo) {
        startIDEFor(
            testInfo,
            LocalProjectInfo(Path("./src/integrationTest/resources/projects/category/single"))
        ) {
            ideFrame {
                openFile("src/CategoryEntity.kt")

                keyboard {
                    hotKey(KeyEvent.VK_ALT, KeyEvent.VK_8)
                }

                selectRelatedFile("CategoryController")

                editorTabs {
                    expectThat(isTabOpened("CategoryController.kt")).isTrue()
                }
            }
        }
    }

    @Test
    fun `should open multiple related files by category`(testInfo: TestInfo) {
        startIDEFor(
            testInfo,
            LocalProjectInfo(Path("./src/integrationTest/resources/projects/category/multiple"))
        ) {
            ideFrame {
                openFile("src/CategoryEntity.kt")

                keyboard {
                    hotKey(KeyEvent.VK_ALT, KeyEvent.VK_8)
                }

                selectRelatedFile("Testing ↲")

                editorTabs {
                    expectThat(isTabOpened("CategoryTest.kt")).isTrue()
                    expectThat(isTabOpened("CategoryTests.kt")).isTrue()
                    expectThat(isTabOpened("CategoryIntegrationTest.kt")).isTrue()
                    expectThat(isTabOpened("CategoryIntegrationTests.kt")).isTrue()
                }
            }
        }
    }
}
