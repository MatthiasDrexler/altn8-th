package de.andrena.tools.altn8th.domain

private const val DIRECTORY_SEPARATOR = '/'

class File(private val path: Collection<String>) {
    private val fileExtensionDelimiter = '.'

    init {
        require(path.isNotEmpty())
    }

    fun nameWithFileExtension(): String = path.last()

    fun nameWithoutFileExtension(): String = path.last().substringBeforeLast(fileExtensionDelimiter)

    fun baseNamesFromPostfixes(postfixes: List<String>): List<String> =
        postfixes
            .map { postfix -> Regex("${postfix}$") }
            .map { postfixRegex -> postfixRegex.replace(nameWithoutFileExtension(), "") }
            .plus(nameWithoutFileExtension())
            .distinct()

    fun path(): String = path.joinToString(separator = DIRECTORY_SEPARATOR.toString())

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

    companion object {
        fun from(path: String): File = File(path.split(DIRECTORY_SEPARATOR))
    }
}