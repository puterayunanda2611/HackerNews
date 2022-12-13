package com.telkom.hackernews.presentation

import com.telkom.hackernews.domain.HackerNewsModel

sealed class HackerNewsViewState {
    data class Success(
        val items: List<HackerNewsModel>
    ): HackerNewsViewState()

    data class Error(val error: Throwable): HackerNewsViewState()

    data class GetMyFavorite(val title: String) : HackerNewsViewState()
}
