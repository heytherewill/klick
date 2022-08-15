package domain

import com.toggl.komposable.architecture.Effect
import com.toggl.komposable.architecture.Mutable
import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.extensions.effectOf
import com.toggl.komposable.extensions.mutateWithoutEffects
import shortcuts.KeyboardEffectProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainReducer @Inject constructor(
    private val keyboardEffectProvider: KeyboardEffectProvider
) : Reducer<AppState, AppAction> {
    override fun reduce(state: Mutable<AppState>, action: AppAction): List<Effect<AppAction>> =
        when (action) {
            AppAction.TrayDoubleClicked -> state.mutateWithoutEffects { copy(windowIsVisible = true) }
            AppAction.CloseWindowClicked -> state.mutateWithoutEffects { copy(windowIsVisible = false) }
            AppAction.ControlPressed -> state.mutateWithoutEffects { copy(modifierKey = ModifierKey.Control) }
            AppAction.ShiftPressed -> state.mutateWithoutEffects { copy(modifierKey = ModifierKey.Shift) }
            AppAction.ControlReleased,
            AppAction.ShiftReleased -> state.mutateWithoutEffects { copy(modifierKey = ModifierKey.None) }
            is AppAction.KeyPressed -> state.withValue<List<Effect<AppAction>>> {
                val layer = when (modifierKey) {
                    ModifierKey.None -> keyboardEffectProvider.regularLayer
                    ModifierKey.Shift -> keyboardEffectProvider.shiftLayer
                    ModifierKey.Control -> keyboardEffectProvider.controlLayer
                }

                effectOf(
                    when (action.actionKey) {
                        ActionKey.Left -> layer.leftKey()
                        ActionKey.Middle -> layer.middleKey()
                        ActionKey.Right -> layer.rightKey()
                    }
                )
            }
        }
}
