package com.telkom.hackernews.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val bag = CompositeDisposable()

    override fun onCleared() {
        bag.clear()
        super.onCleared()
    }

    protected fun Disposable.addToBag() {
        bag.add(this)
    }
}
