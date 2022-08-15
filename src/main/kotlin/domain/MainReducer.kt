package domain

import com.toggl.komposable.architecture.Effect
import com.toggl.komposable.architecture.Mutable
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.mutateWithoutEffects

class MainReducer(


) : Reducer<AppState, AppAction> {
    override fun reduce(state: Mutable<AppState>, action: AppAction): List<Effect<AppAction>> =
        when (action) {
            AppAction.TrayDoubleClicked -> state.mutateWithoutEffects { copy(windowIsVisible = true) }
            AppAction.CloseWindowClicked -> state.mutateWithoutEffects { copy(windowIsVisible = false) }
            AppAction.ControlPressed -> state.mutateWithoutEffects { copy(modifierKey = ModifierKey.Control) }
            AppAction.ShiftPressed -> state.mutateWithoutEffects { copy(modifierKey = ModifierKey.Shift) }
            AppAction.ControlRelease,
            AppAction.ShiftRelease -> state.mutateWithoutEffects { copy(modifierKey = ModifierKey.None) }

            }
        }
}