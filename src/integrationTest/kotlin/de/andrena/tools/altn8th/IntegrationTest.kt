package de.andrena.tools.altn8th

import com.intellij.driver.client.Driver
import com.intellij.driver.sdk.ui.keyboard.RemoteKeyboard
import com.intellij.driver.sdk.waitForIndicators
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.IDEStartResult
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.NoProject
import com.intellij.ide.starter.project.ProjectInfoSpec
import com.intellij.ide.starter.runner.Starter
import com.intellij.openapi.util.SystemInfo
import org.junit.jupiter.api.TestInfo
import java.awt.event.KeyEvent
import java.io.File
import kotlin.time.Duration.Companion.minutes


private const val IDEA_VERSION = "2024.3"

fun startIDEFor(
    testInfo: TestInfo,
    projectInfo: ProjectInfoSpec = NoProject,
    actions: Driver.() -> Unit
): IDEStartResult =
    Starter.newContext(
        testInfo.displayName,
        TestCase(IdeProductProvider.IC, projectInfo).withVersion(IDEA_VERSION)
    )
        .apply {
            PluginConfigurator(this)
                .installPluginFromFolder(File(System.getProperty("path.to.build.plugin")))
        }
        .runIdeWithDriver()
        .useDriverAndCloseIde {
            if (projectInfo !is NoProject) {
                waitForIndicators(1.minutes)
            }

            actions()
        }
