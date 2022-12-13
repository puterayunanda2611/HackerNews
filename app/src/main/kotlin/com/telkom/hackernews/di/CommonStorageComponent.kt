package com.telkom.hackernews.di

import android.content.Context
import com.telkom.hackernews.data.HackerNewsPreference
import com.telkom.hackernews.data.HackerNewsPreferenceImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [HackerNewsStorageModule::class]
)
interface HackerNewsStorageComponent : HackerNewsStorageDeps {

    @Component.Builder
    interface Builder : BaseComponentBuilder<HackerNewsStorageComponent>

    object ComponentFactory : BaseComponentFactory<Context, HackerNewsStorageComponent>() {
        override fun build(param: Context): HackerNewsStorageComponent {
            return DaggerHackerNewsStorageComponent.builder()
                .context(param)
                .build()
        }
    }
}

interface HackerNewsStorageDeps {
    fun provideHackerNewsPreference(): HackerNewsPreference
}

@Module
object HackerNewsStorageModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideHackerNewsPreference(
        context: Context
    ): HackerNewsPreference = HackerNewsPreferenceImpl.getInstance(context)
}
