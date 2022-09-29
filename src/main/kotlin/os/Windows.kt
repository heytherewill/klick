package os

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Windows : Os {
    override val platform = Os.Platform.Windows

    override suspend fun launch(program: Program): Unit = withContext(Dispatchers.IO) {
        val command = when (program) {
            Program.Terminal -> "wt"
            Program.Todoist -> "%appdata%\\..\\Local\\Programs\\todoist\\Todoist.exe"
            is Program.Firefox -> "rundll32 url.dll,FileProtocolHandler ${program.uri}"
            Program.Calendar -> "rundll32 url.dll,FileProtocolHandler https://calendar.google.com"
            Program.Messages -> "slack://app"
        }

        Runtime.getRuntime().exec(command)
    }

    override suspend fun shutdown(): Unit = withContext(Dispatchers.IO) {
        Runtime.getRuntime().exec("shutdown -s")
    }
}
