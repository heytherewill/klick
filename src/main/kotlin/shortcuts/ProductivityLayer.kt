package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction
import os.Os
import os.Program
import javax.inject.Inject

class ProductivityLayer @Inject constructor(
    private val os: Os
) : Layer {
    override fun leftKey() = object : Effect<AppAction> {
        override suspend fun execute() = null
    }

    override fun middleKey() = object : Effect<AppAction> {
        override suspend fun execute(): AppAction? {
            os.launch(Program.Calendar)
            return null
        }
    }

    override fun rightKey() = object : Effect<AppAction> {
        override suspend fun execute(): AppAction? {
            os.launch(Program.Todoist)
            return null
        }
    }
}
