import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import composables.KeyboardEventListener
import composables.KlickWindow
import composables.Settings
import composables.macOSTheme
import composables.windowsTheme
import domain.AppAction
import domain.AppState
import os.Os

fun main() = application {
    val entrypoint = remember { Entrypoint.create() }
    val store = remember { entrypoint.store() }
    val platform = remember { entrypoint.platform() }

    val theme = remember {
        when (platform) {
            Os.Platform.MacOS -> macOSTheme
            Os.Platform.Windows -> windowsTheme
        }
    }
    val state by store.state.collectAsState(AppState())
    val dispatcher: (AppAction) -> Unit = store::dispatch

    KeyboardEventListener(dispatcher) {
        Tray(
            icon = painterResource(
                when (platform) {
                    Os.Platform.Windows -> "tray.png"
                    Os.Platform.MacOS -> "tray-macos.png"
                }
            ),
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
