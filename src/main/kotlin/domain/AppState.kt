package domain

data class AppState(
    val windowIsVisible: Boolean = false,
    val modifierKey: ModifierKey = ModifierKey.None
)
