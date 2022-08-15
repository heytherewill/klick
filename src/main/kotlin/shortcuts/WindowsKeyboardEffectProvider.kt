package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WindowsKeyboardEffectProvider @Inject constructor() : KeyboardEffectProvider {
    private val workPrograms = listOf(
        Program.togglTrack,
        Program.gmail,
        Program.WindowsTerminal,
        Program.Spotify,
        Program.Slack,
        Program.Todoist,
    )

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

    override val shiftLayer: KeyboardEffectProvider.Layer = WindowsShiftLayer()
    override val controlLayer: KeyboardEffectProvider.Layer = WindowsControlLayer()
    override val regularLayer: KeyboardEffectProvider.Layer = WindowsRegularLayer()

    private inner class WindowsRegularLayer : KeyboardEffectProvider.Layer {
        override fun leftKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = withContext(Dispatchers.IO) {
                // Search StackOverflow with clipboard contents
                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                val clipboardContents = clipboard.getData(DataFlavor.stringFlavor) as? String
                val program =
                    if (clipboardContents.isNullOrBlank()) {
                        Program.duckDuckGo
                    } else {
                        Program.stackOverflow(clipboardContents)
                    }
                program.start()
                null
            }
        }

        override fun middleKey()  = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? {
                // Open all work programs
                workPrograms.forEach { it.start() }
                return null
            }
        }

        override fun rightKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = null
        }
    }

    private inner class WindowsShiftLayer : KeyboardEffectProvider.Layer {
        override fun leftKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = null
        }

        override fun middleKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = withContext(Dispatchers.IO) {
                Runtime.getRuntime().exec("shutdown -s")
                null
            }
        }

        override fun rightKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = null
        }
    }

    private inner class WindowsControlLayer : KeyboardEffectProvider.Layer {
        override fun leftKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = null
        }

        override fun middleKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = null
        }

        override fun rightKey() = object : Effect<AppAction> {
            override suspend fun execute(): AppAction? = null
        }
    }
}