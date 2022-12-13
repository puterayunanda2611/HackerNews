package com.telkom.hackernews.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.telkom.hackernews.data.HackerNewsRepository
import com.telkom.hackernews.data.HackerNewsRepositoryImpl
import com.telkom.hackernews.domain.HackerNewsUseCase
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
        HackerNewsStorageDeps::class
    ]
)
interface TopStoryDetailComponent {
    fun inject(activity: TopStoryDetailActivity)

    @Component.Builder
    interface Builder : HackerNewsComponentBuilder<TopStoryDetailComponent> {
        fun storage(deps: HackerNewsStorageDeps): Builder
    }

    object ComponentProvider : HackerNewsComponentProvider<Activity, TopStoryDetailComponent> {
        override fun provide(param: Activity): TopStoryDetailComponent {
            return DaggerTopStoryDetailComponent.builder()
                .storage(HackerNewsStorageComponent.ComponentFactory.get(param))
                .context(param)
                .build()
        }
    }
}

@Module
abstract class TopStoryDetailModule {

    @Binds
    abstract fun bindRepository(impl: HackerNewsRepositoryImpl): HackerNewsRepository

    @Binds
    abstract fun bindUseCase(impl: SetFavoriteUseCase): HackerNewsUseCase<String, Unit>

    @Binds
    @IntoMap
    @ViewModelKey(TopStoryDetailViewModel::class)
    abstract fun vm(impl: TopStoryDetailViewModel): ViewModel
}
