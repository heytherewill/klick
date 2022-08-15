package domain

import com.toggl.komposable.architecture.Reducer
import com.toggl.komposable.architecture.Store
import com.toggl.komposable.extensions.combine
import com.toggl.komposable.extensions.createStore
import com.toggl.komposable.scope.DispatcherProvider
import com.toggl.komposable.scope.StoreScopeProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import javax.inject.Singleton

@Module
interface DomainModule {
    @Provides
    @Singleton
    fun dispatcherProvider() = DispatcherProvider(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Provides
    @Singleton
    @DelicateCoroutinesApi
    fun storeScopeProvider() = StoreScopeProvider { GlobalScope }

    @Binds
    @IntoSet
    fun mainReducer(mainReducer: MainReducer): Store<AppState, AppAction>

    @Provides
    @Singleton
    fun reducer(
        reducers: Set<@JvmSuppressWildcards Reducer<@JvmSuppressWildcards AppState, @JvmSuppressWildcards AppAction>>
    ): Reducer<AppState, AppAction> = combine(*reducers.toTypedArray())

    @Provides
    @Singleton
    fun store(
        reducer: Reducer<AppState, AppAction>,
        dispatcherProvider: DispatcherProvider,
        storeScopeProvider: StoreScopeProvider
    ) = createStore(
        reducer = reducer,
        initialState = AppState(),
        dispatcherProvider = dispatcherProvider,
        storeScopeProvider = storeScopeProvider
    )
}