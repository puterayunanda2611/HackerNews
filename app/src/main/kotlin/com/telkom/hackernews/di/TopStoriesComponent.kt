package com.telkom.hackernews.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.telkom.hackernews.data.ItemResponse
import com.telkom.hackernews.data.TopStoriesRepository
import com.telkom.hackernews.data.TopStoriesRepositoryImpl
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
        CommonStorageDeps::class,
        CommonNetworkDeps::class
    ]
)
interface TopStoriesComponent {
    fun inject(activity: TopStoriesActivity)

    @Component.Builder
    interface Builder : BaseComponentBuilder<TopStoriesComponent> {
        fun storage(deps: CommonStorageDeps): Builder
        fun network(deps: CommonNetworkDeps): Builder
    }

    object ComponentProvider : BaseComponentProvider<Activity, TopStoriesComponent> {
        override fun provide(param: Activity): TopStoriesComponent {
            return DaggerTopStoriesComponent.builder()
                .storage(CommonStorageComponent.ComponentFactory.get(param))
                .network(CommonNetworkComponent.ComponentFactory.get(param))
                .context(param)
                .build()
        }
    }
}

@Module
abstract class TopStoriesModule {

    @Binds
    abstract fun bindRepository(impl: TopStoriesRepositoryImpl): TopStoriesRepository

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
