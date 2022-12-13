package com.telkom.hackernews.domain

import com.telkom.hackernews.data.HackerNewsRepository

class GetFavoriteUseCase(
    private val repository: HackerNewsRepository
) : HackerNewsUseCase<Unit, String> {

    override fun execute(input: Unit): String {
        return repository.getMyFavorite()
    }
}
