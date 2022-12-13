package com.telkom.hackernews.domain

import com.telkom.hackernews.data.HackerNewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetFavoriteUseCase @Inject constructor(
    private val repository: HackerNewsRepository
) : BaseUseCase<String, Unit> {

    override fun execute(input: String) {
        repository.setMyFavorite(input)
    }
}
