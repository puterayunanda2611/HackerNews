package com.telkom.hackernews.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsService {

    @GET("v0/topstories.json")
    fun getTopStoriesId(): Single<List<Long>>

    @GET("v0/item/{id}.json")
    fun getDetailItem(@Path("id") storyId: Long): Single<HackerNewsDetailItemResponse>
}
