package os

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MacOS : Os {
    override val platform = Os.Platform.MacOS

    override suspend fun launch(program: Program): Unit = withContext(Dispatchers.IO) {
        val command = when (program) {
            Program.Terminal -> "iterm2"
            Program.Todoist -> "open -a todoist"
            is Program.Firefox -> "firefox -g ${program.uri}"
            Program.Calendar -> "firefox -g https://calendar.google.com"
            Program.Messages -> "open -a slack"
        }
        Runtime.getRuntime().exec(command)
    }

    override suspend fun shutdown(): Unit = withContext(Dispatchers.IO) {
        Runtime.getRuntime().exec("shutdown -s")
    }
}
