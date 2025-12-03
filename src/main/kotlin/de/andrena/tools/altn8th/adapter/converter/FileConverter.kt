package de.andrena.tools.altn8th.adapter.converter

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import de.andrena.tools.altn8th.domain.File
import java.nio.file.FileSystems

class FileConverter {
    fun fromVirtualFile(virtualFile: VirtualFile): File = File.from(virtualFile.path)

    fun toVirtualFile(file: File): VirtualFile? {
        val path = file.path()
        val nioPath = FileSystems.getDefault().getPath(path)
        return VirtualFileManager.getInstance().findFileByNioPath(nioPath)
    }
}
