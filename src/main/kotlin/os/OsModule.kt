package os

import dagger.Module
import dagger.Provides
import java.util.Locale

@Module
interface OsModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun os(): Os {
            val os = System.getProperty("os.name").lowercase(Locale.getDefault())
            return when {
                os.contains("mac") -> MacOS()
                os.contains("win") -> Windows()
                else -> throw IllegalStateException("Running on an unsupported OS")
            }
        }

        @Provides
        @JvmStatic
        fun platform(os: Os) = os.platform
    }
}
