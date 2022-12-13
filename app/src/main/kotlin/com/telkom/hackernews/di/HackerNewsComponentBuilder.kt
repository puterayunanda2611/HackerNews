package com.telkom.hackernews.di

import android.content.Context
import dagger.BindsInstance

interface HackerNewsComponentBuilder<out C> {

    @BindsInstance
    fun context(context: Context): HackerNewsComponentBuilder<C>

    fun build(): C
}
