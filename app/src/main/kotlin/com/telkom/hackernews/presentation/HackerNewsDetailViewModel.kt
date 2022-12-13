package com.telkom.hackernews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telkom.hackernews.domain.SetFavoriteUseCase

class HackerNewsDetailViewModel(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : BaseViewModel() {
    val state: LiveData<HackerNewsViewState>
        get() = _state

    private val _state: MutableLiveData<HackerNewsViewState> = MutableLiveData()

    fun setFavorite(title: String) {
        setFavoriteUseCase.execute(title)
    }
}
