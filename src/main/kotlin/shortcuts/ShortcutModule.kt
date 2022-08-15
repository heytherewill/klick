package shortcuts

import dagger.Binds
import dagger.Module

@Module
interface ShortcutModule {

    @Binds
    fun keyboardEffectProvider(
        windowsKeyboardEffectProvider: WindowsKeyboardEffectProvider
    ): KeyboardEffectProvider
}
