
import com.toggl.komposable.architecture.Store
import dagger.Component
import domain.AppAction
import domain.AppState
import domain.DomainModule
import shortcuts.ShortcutModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        ShortcutModule::class
    ]
)
internal interface Entrypoint {
    @Singleton
    fun store(): Store<AppState, AppAction>

    companion object {
        @JvmStatic
        fun create(): Entrypoint =
            DaggerEntrypoint.builder().build()
    }
}
