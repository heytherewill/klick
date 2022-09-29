package shortcuts

import dagger.Module
import dagger.Provides

@Module
interface ShortcutModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun keyboardEffectProvider(
            developerLayer: DeveloperLayer,
            productivityLayer: ProductivityLayer,
            hardwareLayer: HardwareLayer
        ) = KeyboardEffectProvider(
            regularLayer = developerLayer,
            shiftLayer = productivityLayer,
            controlLayer = hardwareLayer
        )
    }
}
