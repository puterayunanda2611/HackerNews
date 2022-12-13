package com.telkom.hackernews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telkom.hackernews.domain.GetFavoriteUseCase
import com.telkom.hackernews.domain.GetTopStoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class TopStoriesViewModel(
    private val getTopStoriesUseCase: GetTopStoriesUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : BaseViewModel() {
    val state: LiveData<TopStoriesViewState>
        get() = _state

    private val _state: MutableLiveData<TopStoriesViewState> = MutableLiveData()

    fun loadTopStories() {
        getTopStoriesUseCase
            .execute(Unit)
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  }
            .subscribe({
                _state.value = TopStoriesViewState.Success(it)
            }, {
                _state.value = TopStoriesViewState.Error(it)
            }).addToBag()
    }

    fun getFavorite() {
        _state.value = getFavoriteUseCase.execute(Unit)
            .let { TopStoriesViewState.GetMyFavorite(it) }
    }
}
