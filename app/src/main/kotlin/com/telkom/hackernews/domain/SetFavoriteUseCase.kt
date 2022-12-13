package com.telkom.hackernews.domain

import com.telkom.hackernews.data.HackerNewsRepository

class SetFavoriteUseCase(
    private val repository: HackerNewsRepository
) : HackerNewsUseCase<String, Unit> {

    override fun execute(input: String) {
        repository.setMyFavorite(input)
    }
}
