package com.telkom.hackernews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.telkom.hackernews.domain.GetCommentsUseCase
import com.telkom.hackernews.domain.MyFavoriteModel
import com.telkom.hackernews.domain.SetFavoriteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStoryDetailViewModel @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val getComments: GetCommentsUseCase
) : BaseViewModel() {
    val state: LiveData<TopStoriesDetailViewState>
        get() = _state

    private val _state: MutableLiveData<TopStoriesDetailViewState> = MutableLiveData()

    fun getComments(comments: List<Long>) {
        getComments.execute(comments)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = TopStoriesDetailViewState.Success(it)
            }, {
                _state.value = TopStoriesDetailViewState.Error(it)
            }).addToBag()
    }

    fun setFavorite(id: Long, title: String) {
        MyFavoriteModel(id = id, title = title).let { setFavoriteUseCase.execute(it) }
    }
}
