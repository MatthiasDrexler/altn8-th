package de.andrena.tools.altn8th

import com.intellij.driver.sdk.ui.components.welcomeScreen
import com.intellij.driver.sdk.ui.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

class PluginIntegrationTest {

    @Test
    fun `plugin should be installable`(testInfo: TestInfo) {
        startIDEFor(testInfo) {
            welcomeScreen {
                clickPlugins()
                x { byAccessibleName("Installed") }.click()
                shouldBe("Plugin is installed") {
                    x {
                        and(
                            byVisibleText("AltN8-TH"),
                            byJavaClass("javax.swing.JLabel")
                        )
                    }.present()
                }
            }
        }
    }
}
