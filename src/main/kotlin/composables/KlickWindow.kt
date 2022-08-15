package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import domain.AppAction
import java.awt.Toolkit
import kotlin.math.max

@Composable
fun KlickWindow(
    title: String,
    theme: Theme,
    dispatcher: (AppAction) -> Unit,
    content: @Composable () -> Unit
) {
    Window(
        transparent = true,
        undecorated = true,
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.BottomEnd),
            size = DpSize(400.dp, 110.dp)
        ),
        onCloseRequest = { dispatcher(AppAction.CloseWindowClicked) }
    ) {
        window.subtractInset()

        Column(
            modifier = Modifier
                .background(theme.colors.background, shape = theme.windowShape)
                .shadow(1.dp, spotColor = theme.colors.onBackground, shape = theme.windowShape)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = theme.dimen.defaultPadding)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource("icon.ico"),
                        contentDescription = "App icon",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.size(theme.dimen.halfPadding))

                    Text(
                        text = title,
                        color = theme.colors.onBackground
                    )
                }

                Text(
                    text = "X",
                    color = theme.colors.accent,
                    modifier = Modifier.clickable { dispatcher(AppAction.CloseWindowClicked) }
                )
            }

            content()
        }
    }
}

@Composable
fun ComposeWindow.subtractInset() {
    LaunchedEffect(Unit) {
        val inset = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration)
        val size = Toolkit.getDefaultToolkit().screenSize.apply {
            // Only one side will have an inset at any point in time, all others remain at 0
            // We need to find the one side that has it and apply it to the appropriate dimension
            width -= max(inset.left, inset.right)
            height -= max(inset.top, inset.bottom)
        }
        val rangeX = 0..size.width
        val rangeY = 0..size.height
        // Works when taskbar is on top or left of screen
        if (x !in rangeX || y !in rangeY) {
            setLocation(
                x.coerceIn(rangeX),
                y.coerceIn(rangeY)
            )
        }
        // Works for when taskbar is on right or bottom of screen
        if (x + width !in rangeX || y + height !in rangeY) {
            setLocation(
                (x + width).coerceIn(rangeX) - width,
                (y + height).coerceIn(rangeY) - height
            )
        }
    }
}
