import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import composables.KeyboardEventListener
import composables.KlickWindow
import composables.Settings
import composables.Theme
import domain.AppAction
import domain.AppState

fun main() = application {
    val store = remember { Entrypoint.create().store() }

    val theme = remember { Theme() }
    val state by store.state.collectAsState(AppState())
    val dispatcher: (AppAction) -> Unit = store::dispatch

    KeyboardEventListener(dispatcher) {
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

        if (state.windowIsVisible) {
            KlickWindow(
                title = "Klick Settings",
                dispatcher = dispatcher,
                theme = theme
            ) {
                Settings(theme)
            }
        }
    }
}
