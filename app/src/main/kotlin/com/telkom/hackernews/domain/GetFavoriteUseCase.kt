package com.telkom.hackernews.domain

import com.google.gson.Gson
import com.telkom.hackernews.data.TopStoriesRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: TopStoriesRepository
) : BaseUseCase<Unit, MyFavoriteModel?> {

    override fun execute(input: Unit): MyFavoriteModel? {
        val json = repository.getMyFavorite()
        return try {
            Gson().fromJson(json, MyFavoriteModel::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
