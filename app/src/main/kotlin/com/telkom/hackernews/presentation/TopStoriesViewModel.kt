package com.telkom.hackernews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telkom.hackernews.domain.GetFavoriteUseCase
import com.telkom.hackernews.domain.GetTopStoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStoriesViewModel @Inject constructor(
    private val getTopStoriesUseCase: GetTopStoriesUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : BaseViewModel() {
    val state: LiveData<TopStoriesViewState>
        get() = _state

    private val _state: MutableLiveData<TopStoriesViewState> = MutableLiveData()

    fun loadTopStories() {
        getTopStoriesUseCase
            .execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _state.value = TopStoriesViewState.ShowLoading
            }
            .subscribe({
                _state.value = TopStoriesViewState.Success(it)
            }, {
                _state.value = TopStoriesViewState.HideLoading
                _state.value = TopStoriesViewState.Error(it)
            }, {
                _state.value = TopStoriesViewState.HideLoading
            }).addToBag()
    }

    fun getFavorite() {
        getFavoriteUseCase.execute(Unit)
            .let {
                _state.postValue(TopStoriesViewState.GetMyFavorite(it?.title.orEmpty()))
            }
    }
}
