package com.telkom.hackernews.di

interface BaseComponentProvider<in P, out C> {
    fun provide(param: P): C
}
