package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction
import os.Os
import javax.inject.Inject

class HardwareLayer @Inject constructor(
    private val os: Os
) : Layer {
    override fun leftKey() = object : Effect<AppAction> {
        override suspend fun execute(): AppAction? {
            os.shutdown()
            return null
        }
    }

    override fun middleKey() = object : Effect<AppAction> {
        override suspend fun execute() = null
    }

    override fun rightKey() = object : Effect<AppAction> {
        override suspend fun execute() = null
    }
}
