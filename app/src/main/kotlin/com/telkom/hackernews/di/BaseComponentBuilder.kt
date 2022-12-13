package com.telkom.hackernews.di

import android.content.Context
import dagger.BindsInstance

interface BaseComponentBuilder<out C> {

    @BindsInstance
    fun context(context: Context): BaseComponentBuilder<C>

    fun build(): C
}
