package shortcuts

import com.toggl.komposable.architecture.Effect
import domain.AppAction


interface KeyboardEffectProvider {
    val shiftLayer: Layer
    val controlLayer: Layer
    val regularLayer: Layer

    interface Layer {
        fun leftKey(): Effect<AppAction>
        fun middleKey(): Effect<AppAction>
        fun rightKey(): Effect<AppAction>
    }
}
