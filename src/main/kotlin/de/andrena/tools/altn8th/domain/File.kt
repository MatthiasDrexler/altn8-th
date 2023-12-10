package de.andrena.tools.altn8th.domain

class File(private val path: Collection<String>) {
    companion object {
        private const val DIRECTORY_SEPARATOR = '/'

        fun from(path: String): File = File(path.split(DIRECTORY_SEPARATOR))
    }

    private val dot = '.'

    init {
        require(path.isNotEmpty())
        require(nameWithFileExtension().isNotEmpty())
    }

    fun nameWithFileExtension(): String = path.last()


    fun nameWithoutFileExtension(): String =
        if (isDotFile()) {
            nameWithFileExtension()
        } else path.last().substringBeforeLast(dot)

    fun fileExtension(): String = path.last().substringAfterLast(dot)

    fun baseNamesFromPostfixes(postfixes: List<String>): List<String> =
        postfixes
            .map { postfix -> Regex("${postfix}$") }
            .map { postfixRegex -> postfixRegex.replace(nameWithoutFileExtension(), "") }
            .plus(nameWithoutFileExtension())
            .distinct()

    fun path(): String = path.joinToString(separator = DIRECTORY_SEPARATOR.toString())

    fun relativeFrom(basePath: String): String {
        val basePathIsInFilePath = path().startsWith(basePath)
        if (!basePathIsInFilePath) {
            return path()
        }

        return path()
            .substringAfter(basePath)
            .trimStart(DIRECTORY_SEPARATOR)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        return path == other.path
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }

    override fun toString(): String {
        return "File(path=$path)"
    }

    private fun isDotFile(): Boolean {
        val nonEmptyFilenameSegments = nameWithFileExtension()
            .split(dot)
            .filter { it.isNotEmpty() }

        val onlyOneNonEmptySegment = nonEmptyFilenameSegments.size <= 1
        val startsWithDot = nameWithFileExtension().startsWith(dot)
        return onlyOneNonEmptySegment && startsWithDot
    }
}
