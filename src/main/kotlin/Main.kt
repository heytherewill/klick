import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import com.toggl.komposable.scope.StoreScopeProvider
import composables.KeyboardEventListener
import domain.AppAction
import domain.AppState

fun main() = application {
    val store = remember { Entrypoint.create().store() }

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


    }
}