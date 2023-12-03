package de.andrena.tools.altn8th.adapter

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentIterator
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.domain.File

class ProjectFiles {
    fun allOf(project: Project): List<File> {
        val fileIndex = ProjectRootManager.getInstance(project).fileIndex

        val listVirtualFilesIterator = ListVirtualFilesIterator()
        fileIndex.iterateContent(listVirtualFilesIterator)

        val fileConverter = FileConverter()
        return listVirtualFilesIterator.virtualFiles()
            .map { fileConverter.fromVirtualFile(it) }
    }

    private class ListVirtualFilesIterator : ContentIterator {
        companion object {
            private const val CONTINUE_TO_ITERATE = true
        }

        private val files = ArrayList<VirtualFile>()

        override fun processFile(fileOrDir: VirtualFile): Boolean {
            if (fileOrDir.isDirectory) {
                return CONTINUE_TO_ITERATE
            }

            files.add(fileOrDir)

            return CONTINUE_TO_ITERATE
        }

        fun virtualFiles(): List<VirtualFile> = files
    }
}