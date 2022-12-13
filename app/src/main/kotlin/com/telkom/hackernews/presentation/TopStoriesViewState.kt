package com.telkom.hackernews.presentation

import com.telkom.hackernews.domain.TopStoryModel

sealed class TopStoriesViewState {
    data class Success(
        val items: List<TopStoryModel>
    ): TopStoriesViewState()

    data class Error(val error: Throwable): TopStoriesViewState()

    data class GetMyFavorite(val title: String) : TopStoriesViewState()
}
