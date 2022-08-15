package shortcuts

import dagger.Module
import dagger.Provides
import java.util.*

@Module
interface ShortcutModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun keyboardEffectProvider(): KeyboardEffectProvider {
            val os = System.getProperty("os.name").lowercase(Locale.getDefault())
            return when {
                os.contains("mac") -> MacOSKeyboardEffectProvider()
                os.contains("win") -> WindowsKeyboardEffectProvider()
                else -> throw IllegalStateException("Running on an unsupported OS")
            }
        }
    }
}
