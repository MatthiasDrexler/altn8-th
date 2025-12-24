package de.andrena.tools.altn8th

import com.intellij.driver.client.Driver
import com.intellij.driver.sdk.ui.components.*
import com.intellij.driver.sdk.ui.present
import com.intellij.driver.sdk.ui.shouldBe
import com.intellij.driver.sdk.ui.xQuery
import com.intellij.driver.sdk.waitForIndicators
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.IDEStartResult
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.project.NoProject
import com.intellij.ide.starter.project.ProjectInfoSpec
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.TestInfo
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

fun stopForUIAnalysis() = Thread.sleep(30.minutes.inWholeMilliseconds)

fun IdeaFrameUI.selectRelatedFile(entry: String) {
    val relatedFilesPopup = popup(xQuery { byVisibleText("Related Files") })
    relatedFilesPopup.shouldBe(present)

    val relatedFilesList = jBlist(xQuery { contains(byVisibleText(entry)) })
    relatedFilesList.shouldBe(present)

    val index = relatedFilesList.rawItems.indexOfFirst { it.contains(entry) }
    keyboard {
        (0 until index).forEach { selectedIndex ->
            if (wasSkipped(relatedFilesList, selectedIndex).not()) {
                down()
            }
        }
        enter()
    }

    waitForNoOpenedDialogs()
}

private fun wasSkipped(
    relatedFilesList: JListUiComponent,
    selectedIndex: Int
): Boolean =
    secondLastCellWasCategory(relatedFilesList, selectedIndex)
        && lastCellWasFile(relatedFilesList, selectedIndex)
        && (currentCellIsCategory(relatedFilesList, selectedIndex))

private fun secondLastCellWasCategory(
    relatedFilesList: JListUiComponent,
    selectedIndex: Int
): Boolean =
    relatedFilesList.rawItems.getOrNull(selectedIndex - 2)?.contains("CategoryCell") ?: true

private fun lastCellWasFile(
    relatedFilesList: JListUiComponent,
    selectedIndex: Int
): Boolean =
    relatedFilesList.rawItems.getOrNull(selectedIndex - 1)?.contains("FileCell") ?: true

private fun currentCellIsCategory(
    relatedFilesList: JListUiComponent,
    selectedIndex: Int
): Boolean =
    relatedFilesList.rawItems.getOrNull(selectedIndex)?.contains("CategoryCell") ?: true
