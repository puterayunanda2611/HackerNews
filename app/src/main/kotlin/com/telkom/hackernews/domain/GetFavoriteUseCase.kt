package com.telkom.hackernews.domain

import com.telkom.hackernews.data.HackerNewsRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: HackerNewsRepository
) : BaseUseCase<Unit, String> {

    override fun execute(input: Unit): String {
        return repository.getMyFavorite()
    }
}
