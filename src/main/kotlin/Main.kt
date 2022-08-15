import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import composables.KeyboardEventListener
import domain.AppAction
import domain.AppState
import shortcuts.WindowsKeyboardHandler

fun main() = application {
    val handler = WindowsKeyboardHandler()
    val store = remember { Entrypoint.create().store() }

    val state by store.state.collectAsState(AppState())
    val dispatcher: (AppAction) -> Unit = store::dispatch

    KeyboardEventListener(handler) {
        Tray(
            icon = painterResource("tray.png"),
            onAction = { dispatcher(AppAction.TrayDoubleClicked) },
            menu = {
                Item(
                    text = "Exit",
                    onClick = { exitApplication() }
                )
            }
        )
    }
}


object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)
    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}