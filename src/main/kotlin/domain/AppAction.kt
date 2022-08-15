package domain

sealed interface AppAction {
    object TrayDoubleClicked : AppAction
    object CloseWindowClicked : AppAction

    object ShiftPressed : AppAction
    object ShiftRelease : AppAction

    object ControlPressed : AppAction
    object ControlRelease : AppAction
}

