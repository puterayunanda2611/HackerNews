package com.telkom.hackernews.data

import com.telkom.hackernews.data.HackerNewsPreferenceKeys.PREFERENCE_KEY_MY_FAVORITE_STORY
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface HackerNewsRepository {

    fun getTopStoriesId(): Single<List<Long>>

    fun getDetailItem(storyId: Long): Single<HackerNewsDetailItemResponse>

    fun getMyFavorite(): String

    fun setMyFavorite(value: String)
}

@Singleton
class HackerNewsRepositoryImpl @Inject constructor(
    private val service: HackerNewsService,
    private val storage: HackerNewsPreference
) : HackerNewsRepository {

    override fun getTopStoriesId(): Single<List<Long>> {
        return service.getTopStoriesId()
    }

    override fun getDetailItem(storyId: Long): Single<HackerNewsDetailItemResponse> {
        return service.getDetailItem(storyId)
    }

    override fun getMyFavorite(): String {
        return storage.get(PREFERENCE_KEY_MY_FAVORITE_STORY, "").orEmpty()
    }

    override fun setMyFavorite(value: String) {
        storage.set(PREFERENCE_KEY_MY_FAVORITE_STORY, value)
    }
}
