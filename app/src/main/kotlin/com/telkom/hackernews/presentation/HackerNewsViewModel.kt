package com.telkom.hackernews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telkom.hackernews.domain.GetFavoriteUseCase
import com.telkom.hackernews.domain.GetTopStoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HackerNewsViewModel(
    private val getTopStoriesUseCase: GetTopStoriesUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : BaseViewModel() {
    val state: LiveData<HackerNewsViewState>
        get() = _state

    private val _state: MutableLiveData<HackerNewsViewState> = MutableLiveData()

    fun loadTopStories() {
        getTopStoriesUseCase
            .execute(Unit)
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  }
            .subscribe({
                _state.value = HackerNewsViewState.Success(it)
            }, {
                _state.value = HackerNewsViewState.Error(it)
            }).addToBag()
    }

    fun getFavorite() {
        _state.value = getFavoriteUseCase.execute(Unit)
            .let { HackerNewsViewState.GetMyFavorite(it) }
    }
}
