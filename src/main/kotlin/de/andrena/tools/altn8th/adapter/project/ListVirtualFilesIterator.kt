package de.andrena.tools.altn8th.adapter.project

import com.intellij.openapi.roots.ContentIterator
import com.intellij.openapi.vfs.VirtualFile

class ListVirtualFilesIterator : ContentIterator {
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

    fun list(): List<VirtualFile> = files
}