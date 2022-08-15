import com.toggl.komposable.architecture.Store
import dagger.Component
import domain.AppAction
import domain.AppState
import domain.DomainModule
import javax.inject.Singleton

@Component(
    modules = [
        DomainModule::class,
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