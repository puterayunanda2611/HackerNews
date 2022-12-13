package com.telkom.hackernews.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.telkom.hackernews.data.ItemResponse
import com.telkom.hackernews.data.HackerNewsRepository
import com.telkom.hackernews.data.HackerNewsRepositoryImpl
import com.telkom.hackernews.domain.*
import com.telkom.hackernews.presentation.TopStoriesViewModel
import com.telkom.hackernews.ui.TopStoriesActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TopStoriesModule::class
    ],
    dependencies = [
        HackerNewsStorageDeps::class,
        HackerNewsNetworkDeps::class
    ]
)
interface TopStoriesComponent {
    fun inject(activity: TopStoriesActivity)

    @Component.Builder
    interface Builder : BaseComponentBuilder<TopStoriesComponent> {
        fun storage(deps: HackerNewsStorageDeps): Builder
        fun network(deps: HackerNewsNetworkDeps): Builder
    }

    object ComponentProvider : BaseComponentProvider<Activity, TopStoriesComponent> {
        override fun provide(param: Activity): TopStoriesComponent {
            return DaggerTopStoriesComponent.builder()
                .storage(HackerNewsStorageComponent.ComponentFactory.get(param))
                .network(HackerNewsNetworkComponent.ComponentFactory.get(param))
                .context(param)
                .build()
        }
    }
}

@Module
abstract class TopStoriesModule {

    @Binds
    abstract fun bindRepository(impl: HackerNewsRepositoryImpl): HackerNewsRepository

    @Binds
    abstract fun bindTransformer(impl: GetTopStoriesTransformer):
            BaseTransformer<ItemResponse, TopStoryModel>

    @Binds
    abstract fun bindUseCase(impl: GetTopStoriesUseCase):
            BaseUseCase<Unit, Observable<TopStoryModel>>

    @Binds
    @IntoMap
    @ViewModelKey(TopStoriesViewModel::class)
    abstract fun vm(impl: TopStoriesViewModel): ViewModel
}
