package com.telkom.hackernews.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.telkom.hackernews.data.HackerNewsService
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Singleton
@Component(
    modules = [HackerNewsNetworkModule::class]
)
interface HackerNewsNetworkComponent: HackerNewsNetworkDeps {
    @Component.Builder
    interface Builder : HackerNewsComponentBuilder<HackerNewsNetworkComponent>

    object ComponentFactory : HackerNewsComponentFactory<Context, HackerNewsNetworkComponent>() {
        override fun build(param: Context): HackerNewsNetworkComponent {
            return DaggerHackerNewsNetworkComponent.builder()
                .context(param)
                .build()
        }
    }
}

interface HackerNewsNetworkDeps {
    fun provideService(): HackerNewsService
}

@Module
object HackerNewsNetworkModule {

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
            .baseUrl("https://hacker-news.firebaseio.com/")
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideService(retrofit: Retrofit): HackerNewsService {
        return retrofit.create(HackerNewsService::class.java)
    }
}
