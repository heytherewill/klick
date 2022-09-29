package composables

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition

val windowsTheme = Theme(
    window = WindowTheme(
        shape = CutCornerShape(topStart = 20.dp),
        startingPosition = WindowPosition.Aligned(Alignment.BottomEnd)
    )
)

val macOSTheme = Theme(
    window = WindowTheme(
        shape = CutCornerShape(bottomStart = 20.dp),
        startingPosition = WindowPosition.Aligned(Alignment.TopEnd)
    )
)

data class Theme(
    val colors: Colors = Colors(),
    val dimen: Dimen = Dimen(),
    val window: WindowTheme
)

data class WindowTheme(
    val shape: Shape,
    val startingPosition: WindowPosition
)

data class Colors(
    val onBackground: Color = Color.White,
    val background: Color = Color(0xFF121213),
    val accent: Color = Color(0xFFf6880a)
)

data class Dimen(
    val defaultPadding: Dp = 20.dp,
    val halfPadding: Dp = defaultPadding / 2
)
