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

    @Binds
    @IntoSet
    fun mainReducer(mainReducer: MainReducer): Reducer<AppState, AppAction>

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun dispatcherProvider() = DispatcherProvider(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Default
        )

        @Provides
        @Singleton
        @DelicateCoroutinesApi
        @JvmStatic
        fun storeScopeProvider() = StoreScopeProvider { GlobalScope }

        @Provides
        @Singleton
        @JvmStatic
        fun reducer(
            reducers: Set<@JvmSuppressWildcards Reducer<@JvmSuppressWildcards AppState, @JvmSuppressWildcards AppAction>>
        ): Reducer<AppState, AppAction> = combine(*reducers.toTypedArray())

        @Provides
        @Singleton
        @JvmStatic
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
}