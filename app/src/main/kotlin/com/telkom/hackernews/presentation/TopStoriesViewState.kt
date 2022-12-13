package com.telkom.hackernews.presentation

import com.telkom.hackernews.domain.HackerNewsModel

sealed class TopStoriesViewState {
    data class Success(
        val items: List<HackerNewsModel>
    ): TopStoriesViewState()

    data class Error(val error: Throwable): TopStoriesViewState()

    data class GetMyFavorite(val title: String) : TopStoriesViewState()
}
