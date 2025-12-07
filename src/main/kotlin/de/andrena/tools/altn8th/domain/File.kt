package de.andrena.tools.altn8th.domain

data class File(private val path: Collection<String>) {
    companion object {
        private const val DIRECTORY_SEPARATOR = '/'
        private const val DOT = '.'

        fun from(path: String): File = File(path.split(DIRECTORY_SEPARATOR))
    }


    init {
        require(path.isNotEmpty())
        require(nameWithFileExtension().isNotEmpty())
    }

    fun nameWithFileExtension(): String = path.last()

    fun escapedNameWithFileExtension(): String = Regex.escape(nameWithFileExtension())

    fun nameWithoutFileExtension(): String =
        if (isDotFile()) {
            nameWithFileExtension()
        } else path.last().substringBeforeLast(DOT)

    fun escapedNameWithoutFileExtension(): String = Regex.escape(nameWithoutFileExtension())

    fun fileExtension(): String = path.last().substringAfterLast(DOT)

    fun path(): String = path.joinToString(separator = DIRECTORY_SEPARATOR.toString())

    fun escapedPath(): String = Regex.escape(path())

    fun relativeFrom(basePath: String): String {
        val basePathIsInFilePath = path().startsWith(basePath)
        if (!basePathIsInFilePath) {
            return path()
        }

        return path()
            .substringAfter(basePath)
            .trimStart(DIRECTORY_SEPARATOR)
    }

    override fun toString(): String {
        return "File(path=$path)"
    }

    private fun isDotFile(): Boolean {
        val nonEmptyFilenameSegments = nameWithFileExtension()
            .split(DOT)
            .filter { it.isNotEmpty() }

        val onlyOneNonEmptySegment = nonEmptyFilenameSegments.size <= 1
        val startsWithDot = nameWithFileExtension().startsWith(DOT)
        return onlyOneNonEmptySegment && startsWithDot
    }
}
