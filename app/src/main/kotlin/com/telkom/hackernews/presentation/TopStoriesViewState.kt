package com.telkom.hackernews.presentation

import com.telkom.hackernews.domain.TopStoryModel

sealed class TopStoriesViewState {
    object HideLoading : TopStoriesViewState()

    object ShowLoading : TopStoriesViewState()

    data class Success(
        val items: TopStoryModel
    ): TopStoriesViewState()

    data class Error(val error: Throwable): TopStoriesViewState()

    data class GetMyFavorite(val title: String) : TopStoriesViewState()
}
