package os

sealed interface Program {
    object Terminal : Program
    object Messages : Program
    object Todoist : Program
    object Calendar : Program
    data class Firefox(val uri: String) : Program

    companion object {
        val duckDuckGo = Firefox("https://duckduckgo.com")
        fun duckDuckGo(query: String) = Firefox("https://duckduckgo.com/?q=$query")
        fun stackOverflow(query: String) = Firefox("https://stackoverflow.com/search?q=$query")
    }
}
