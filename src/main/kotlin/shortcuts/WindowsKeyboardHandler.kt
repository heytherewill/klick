package shortcuts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class WindowsKeyboardHandler : KeyboardHandler {
    private val workPrograms = listOf(
        Program.togglTrack,
        Program.gmail,
        Program.WindowsTerminal,
        Program.Spotify,
        Program.Slack,
        Program.Todoist,
    )

    override suspend fun handleLeftKeyPress(shift: Boolean) {

        suspend fun searchStackOverflow() {
            withContext(Dispatchers.IO) {
                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                val clipboardContents = clipboard.getData(DataFlavor.stringFlavor) as? String
                val program =
                    if (clipboardContents.isNullOrBlank()) { Program.duckDuckGo }
                    else { Program.stackOverflow(clipboardContents) }
                program.start()
            }
        }

        if (!shift) {
            searchStackOverflow()
        }
    }

    override suspend fun handleMiddleKeyPress(shift: Boolean) {
        fun startWorkPrograms() {
            workPrograms.forEach { it.start() }
        }

        fun shutTheComputerDown() {
            Runtime.getRuntime().exec("shutdown -s")
        }

        if (shift) {
            shutTheComputerDown()
        } else {
            startWorkPrograms()
        }
    }

    override suspend fun handleRightKeyPress(shift: Boolean) {
    }

    private fun Program.start() {
        val command = when (this) {
            Program.WindowsTerminal -> "wt"
            Program.Slack -> "slack://app"
            Program.Spotify -> "spotify"
            Program.VisualStudioCode -> "code"
            Program.Todoist -> "%appdata%\\..\\Local\\Programs\\todoist\\Todoist.exe"
            Program.BluetoothSettings -> "ms-settings:bluetooth"
            is Program.Firefox -> "rundll32 url.dll,FileProtocolHandler $uri"
        }

        Runtime.getRuntime().exec(command)
    }
}