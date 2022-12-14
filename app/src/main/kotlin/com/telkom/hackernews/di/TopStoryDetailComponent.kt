package com.telkom.hackernews.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.telkom.hackernews.data.TopStoriesRepository
import com.telkom.hackernews.data.TopStoriesRepositoryImpl
import com.telkom.hackernews.domain.BaseUseCase
import com.telkom.hackernews.domain.MyFavoriteModel
import com.telkom.hackernews.domain.SetFavoriteUseCase
import com.telkom.hackernews.presentation.TopStoryDetailViewModel
import com.telkom.hackernews.ui.TopStoryDetailActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TopStoryDetailModule::class
    ],
    dependencies = [
        CommonNetworkDeps::class,
        CommonStorageDeps::class
    ]
)
interface TopStoryDetailComponent {
    fun inject(activity: TopStoryDetailActivity)

    @Component.Builder
    interface Builder : BaseComponentBuilder<TopStoryDetailComponent> {
        fun storage(deps: CommonStorageDeps): Builder
        fun network(deps: CommonNetworkDeps): Builder
    }

    object ComponentProvider : BaseComponentProvider<Activity, TopStoryDetailComponent> {
        override fun provide(param: Activity): TopStoryDetailComponent {
            return DaggerTopStoryDetailComponent.builder()
                .storage(CommonStorageComponent.ComponentFactory.get(param))
                .network(CommonNetworkComponent.ComponentFactory.get(param))
                .context(param)
                .build()
        }
    }
}

@Module
abstract class TopStoryDetailModule {

    @Binds
    abstract fun bindRepository(impl: TopStoriesRepositoryImpl): TopStoriesRepository

    @Binds
    abstract fun bindUseCase(impl: SetFavoriteUseCase): BaseUseCase<MyFavoriteModel, Unit>

    @Binds
    @IntoMap
    @ViewModelKey(TopStoryDetailViewModel::class)
    abstract fun vm(impl: TopStoryDetailViewModel): ViewModel
}
