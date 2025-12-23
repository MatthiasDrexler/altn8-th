import com.intellij.driver.sdk.ui.components.welcomeScreen
import com.intellij.driver.sdk.ui.shouldBe
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.NoProject
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.Test
import java.io.File

class PluginTest {

    @Test
    fun `plugin should be installable`() {
        Starter.newContext(
            "Check plugin installation",
            TestCase(IdeProductProvider.IC, NoProject).withVersion("2024.3")
        ).apply {
            PluginConfigurator(this)
                .installPluginFromFolder(File(System.getProperty("path.to.build.plugin")))
        }
            .runIdeWithDriver()
            .useDriverAndCloseIde {
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
