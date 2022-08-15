package composables

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Theme(
    val colors: Colors = Colors(),
    val dimen: Dimen = Dimen(),
    val windowShape: Shape = CutCornerShape(topStart = 20.dp)
)

data class Colors(
    val onBackground: Color = Color.White,
    val background: Color = Color(0xFF313131),
    val accent: Color = Color(0xFFf6880a)
)

data class Dimen(
    val defaultPadding: Dp = 20.dp,
    val halfPadding: Dp = defaultPadding / 2
)
