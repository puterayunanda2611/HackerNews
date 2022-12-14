package com.telkom.hackernews.domain

import com.google.gson.Gson
import com.telkom.hackernews.data.TopStoriesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetFavoriteUseCase @Inject constructor(
    private val repository: TopStoriesRepository
) : BaseUseCase<MyFavoriteModel, Unit> {

    override fun execute(input: MyFavoriteModel) {
        val item = Gson().toJson(input)
        repository.setMyFavorite(item)
    }
}
