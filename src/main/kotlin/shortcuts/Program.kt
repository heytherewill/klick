package shortcuts

sealed interface Program {
    object WindowsTerminal : Program
    object Slack : Program
    object Spotify : Program
    object VisualStudioCode : Program
    object Todoist : Program
    object BluetoothSettings : Program
    data class Firefox(val uri: String) : Program

    companion object {
        val gmail = Program.Firefox("https://gmail.com")
        val duckDuckGo = Program.Firefox("https://duckduckgo.com")
        val togglTrack = Program.Firefox("https://track.toggl.com/")
        fun stackOverflow(query: String) = Program.Firefox ("https://stackoverflow.com/search?q=$query")
    }
}