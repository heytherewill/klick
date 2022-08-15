package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import domain.ActionKey
import domain.AppAction

@Composable
fun KeyboardEventListener(
    dispatcher: (AppAction) -> Unit,
    content: @Composable () -> Unit
) {
    val nativeKeyListener = remember(dispatcher) {
        KeyboardHandlerKeyListener(dispatcher)
    }

    content()

    DisposableEffect(nativeKeyListener) {
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(nativeKeyListener)

        onDispose {
            GlobalScreen.unregisterNativeHook()
        }
    }
}

class KeyboardHandlerKeyListener(
    private val dispatcher: (AppAction) -> Unit
) : NativeKeyListener {

    private val shift = NativeKeyEvent.VC_SHIFT
    private val control = NativeKeyEvent.VC_CONTROL
    private val leftButton = NativeKeyEvent.VC_F17
    private val middleButton = NativeKeyEvent.VC_F18
    private val rightButton = NativeKeyEvent.VC_F19

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        when (e.keyCode) {
            shift -> dispatcher(AppAction.ShiftPressed)
            control -> dispatcher(AppAction.ControlPressed)
            leftButton -> dispatcher(AppAction.KeyPressed(ActionKey.Left))
            middleButton -> dispatcher(AppAction.KeyPressed(ActionKey.Middle))
            rightButton -> dispatcher(AppAction.KeyPressed(ActionKey.Right))
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        when (e.keyCode) {
            shift -> dispatcher(AppAction.ShiftReleased)
            control -> dispatcher(AppAction.ControlReleased)
        }
    }
}