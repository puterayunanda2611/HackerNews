package com.telkom.hackernews.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.telkom.hackernews.data.TopStoriesService
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Singleton
@Component(
    modules = [CommonNetworkModule::class]
)
interface CommonNetworkComponent: CommonNetworkDeps {
    @Component.Builder
    interface Builder : BaseComponentBuilder<CommonNetworkComponent>

    object ComponentFactory : BaseComponentFactory<Context, CommonNetworkComponent>() {
        override fun build(param: Context): CommonNetworkComponent {
            return DaggerCommonNetworkComponent.builder()
                .context(param)
                .build()
        }
    }
}

interface CommonNetworkDeps {
    fun provideService(): TopStoriesService
}

@Module
object CommonNetworkModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideHttpCache(context: Context): Cache {
        val cacheSize = 10L * 1024L * 1024L
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        return client.build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://hacker-news.firebaseio.com/")
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideService(retrofit: Retrofit): TopStoriesService {
        return retrofit.create(TopStoriesService::class.java)
    }
}
