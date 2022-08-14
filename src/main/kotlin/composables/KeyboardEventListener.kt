package composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import shortcuts.KeyboardHandler

@Composable
fun KeyboardEventListener(
    keyboardHandler: KeyboardHandler,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val nativeKeyListener = remember(keyboardHandler) {
        KeyboardHandlerKeyListener(scope, keyboardHandler)
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
    private val scope: CoroutineScope,
    private val keyboardHandler: KeyboardHandler
) : NativeKeyListener {

    private val shift = NativeKeyEvent.VC_SHIFT
    private val leftButton = NativeKeyEvent.VC_F17
    private val middleButton = NativeKeyEvent.VC_F18
    private val rightButton = NativeKeyEvent.VC_F19

    private var useShift = false

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        when (e.keyCode) {
            shift -> useShift = true
            leftButton -> scope.launch(Dispatchers.IO) { keyboardHandler.handleLeftKeyPress(useShift) }
            middleButton -> scope.launch(Dispatchers.IO) { keyboardHandler.handleMiddleKeyPress(useShift) }
            rightButton -> scope.launch(Dispatchers.IO) { keyboardHandler.handleRightKeyPress(useShift) }
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        if (e.keyCode == shift) {
            useShift = false
        }
    }
}