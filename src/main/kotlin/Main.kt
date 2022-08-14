// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import composables.KeyboardEventListener
import shortcuts.WindowsKeyboardHandler

fun main() = application {
    val handler = WindowsKeyboardHandler()

    KeyboardEventListener(handler) {
        Tray(
            icon = painterResource("tray.png"),
            menu = {
                Item(
                    "Exit",
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