package domain

sealed interface AppAction {
    object TrayDoubleClicked : AppAction
    object CloseWindowClicked : AppAction

    object ShiftPressed : AppAction
    object ShiftReleased : AppAction

    object ControlPressed : AppAction
    object ControlReleased : AppAction

    data class KeyPressed(val actionKey: ActionKey) : AppAction
}

