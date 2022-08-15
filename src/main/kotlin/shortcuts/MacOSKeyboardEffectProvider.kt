package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class MacOSKeyboardEffectProvider : KeyboardEffectProvider {
    private val workPrograms = listOf(
        Program.togglTrack,
        Program.gmail,
        Program.Terminal,
        Program.Spotify,
        Program.Slack,
        Program.Todoist
    )

    private fun Program.start() {
        val command = when (this) {
            Program.Terminal -> "iterm2"
            Program.Slack -> "open -a slack"
            Program.Spotify -> "open -a spotify"
            Program.VisualStudioCode -> "code"
            Program.Todoist -> "open -a todoist"
            Program.BluetoothSettings -> "ms-settings:bluetooth"
            is Program.Firefox -> "firefox -g $uri"
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

        override fun middleKey() = object : Effect<AppAction> {
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
