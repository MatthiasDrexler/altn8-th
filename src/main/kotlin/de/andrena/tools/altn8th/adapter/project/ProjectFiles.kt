package de.andrena.tools.altn8th.adapter.project

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import de.andrena.tools.altn8th.adapter.converter.FileConverter
import de.andrena.tools.altn8th.domain.File

class ProjectFiles {
    fun allOf(project: Project): List<File> {
        val fileIndex = ProjectRootManager.getInstance(project).fileIndex

        val listVirtualFilesIterator = ListVirtualFilesIterator()
        fileIndex.iterateContent(listVirtualFilesIterator)

        val fileConverter = FileConverter()
        return listVirtualFilesIterator.list().map { fileConverter.fromVirtualFile(it) }
    }
}