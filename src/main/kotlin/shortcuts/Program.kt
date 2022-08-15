package shortcuts

sealed interface Program {
    object Terminal : Program
    object Slack : Program
    object Spotify : Program
    object VisualStudioCode : Program
    object Todoist : Program
    object BluetoothSettings : Program
    data class Firefox(val uri: String) : Program

    companion object {
        val gmail = Firefox("https://gmail.com")
        val duckDuckGo = Firefox("https://duckduckgo.com")
        val togglTrack = Firefox("https://track.toggl.com/")
        fun stackOverflow(query: String) = Firefox("https://stackoverflow.com/search?q=$query")
    }
}
