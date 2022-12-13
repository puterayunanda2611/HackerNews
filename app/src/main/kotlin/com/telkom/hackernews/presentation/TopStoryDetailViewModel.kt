package com.telkom.hackernews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telkom.hackernews.domain.SetFavoriteUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStoryDetailViewModel @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : BaseViewModel() {
    val state: LiveData<TopStoriesViewState>
        get() = _state

    private val _state: MutableLiveData<TopStoriesViewState> = MutableLiveData()

    fun setFavorite(title: String) {
        setFavoriteUseCase.execute(title)
    }
}
