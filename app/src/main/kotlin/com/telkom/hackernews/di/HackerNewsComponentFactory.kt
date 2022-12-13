package com.telkom.hackernews.di

import androidx.annotation.GuardedBy

abstract class HackerNewsComponentFactory<in P, out C> {
    @Volatile
    @GuardedBy("lock")
    private var component: C? = null
    private val lock = Any()

    fun get(param: P): C {
        if (component == null) {
            synchronized(lock) {
                if (component == null) {
                    component = build(param)
                }
            }
        }

        return component!!
    }

    protected abstract fun build(param: P): C
}
