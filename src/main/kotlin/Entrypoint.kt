import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.architecture.Store
import com.toggl.komposable.scope.DispatcherProvider
import dagger.Component
import domain.AppAction
import domain.AppState
import domain.DomainModule
import kotlinx.coroutines.CoroutineScope
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

    @Singleton
    fun dispatcherProvider(): DispatcherProvider

    companion object {
        @JvmStatic
        fun create(): Entrypoint =
            DaggerEntrypoint.builder().build()
    }
}