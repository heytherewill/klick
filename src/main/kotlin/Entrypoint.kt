
import com.toggl.komposable.architecture.Store
import dagger.Component
import domain.AppAction
import domain.AppState
import domain.DomainModule
import os.Os
import os.OsModule
import shortcuts.ShortcutModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        ShortcutModule::class,
        OsModule::class
    ]
)
internal interface Entrypoint {
    @Singleton
    fun store(): Store<AppState, AppAction>

    @Singleton
    fun platform(): Os.Platform

    companion object {
        @JvmStatic
        fun create(): Entrypoint =
            DaggerEntrypoint.builder().build()
    }
}
