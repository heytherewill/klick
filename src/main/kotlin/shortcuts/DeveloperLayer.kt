package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction
import os.Os
import os.Program
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import javax.inject.Inject

class DeveloperLayer @Inject constructor(
    private val os: Os
) : Layer {

    private val developerSymbols = Regex("""[<>\[\](){}|/\\]""")

    override fun leftKey() = object : Effect<AppAction> {
        override suspend fun execute(): AppAction? {
            val query = readClipboardAsString()
            val program =
                if (query.isNullOrBlank()) {
                    Program.duckDuckGo
                } else if (query.containDeveloperSymbols()) {
                    Program.stackOverflow(query)
                } else {
                    Program.duckDuckGo(query)
                }

            os.launch(program)
            return null
        }
    }

    override fun middleKey() = object : Effect<AppAction> {
        override suspend fun execute() = null
    }

    override fun rightKey() = object : Effect<AppAction> {
        override suspend fun execute() = null
    }

    private fun String.containDeveloperSymbols() =
        developerSymbols.containsMatchIn(this)

    private fun readClipboardAsString() =
        Toolkit.getDefaultToolkit()
            .systemClipboard
            .getData(DataFlavor.stringFlavor) as? String
}
