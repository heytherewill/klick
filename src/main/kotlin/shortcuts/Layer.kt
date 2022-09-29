package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction

interface Layer {
    fun leftKey(): Effect<AppAction>
    fun middleKey(): Effect<AppAction>
    fun rightKey(): Effect<AppAction>
}
