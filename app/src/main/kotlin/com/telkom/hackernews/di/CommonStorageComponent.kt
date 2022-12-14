package com.telkom.hackernews.di

import android.content.Context
import com.telkom.hackernews.data.TopStoriesPreference
import com.telkom.hackernews.data.TopStoriesPreferenceImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CommonStorageModule::class]
)
interface CommonStorageComponent : CommonStorageDeps {

    @Component.Builder
    interface Builder : BaseComponentBuilder<CommonStorageComponent>

    object ComponentFactory : BaseComponentFactory<Context, CommonStorageComponent>() {
        override fun build(param: Context): CommonStorageComponent {
            return DaggerCommonStorageComponent.builder()
                .context(param)
                .build()
        }
    }
}

interface CommonStorageDeps {
    fun provideHackerNewsPreference(): TopStoriesPreference
}

@Module
object CommonStorageModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideHackerNewsPreference(
        context: Context
    ): TopStoriesPreference = TopStoriesPreferenceImpl.getInstance(context)
}
