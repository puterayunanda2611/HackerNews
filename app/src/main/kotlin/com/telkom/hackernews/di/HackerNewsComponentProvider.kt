package com.telkom.hackernews.di

interface HackerNewsComponentProvider<in P, out C> {
    fun provide(param: P): C
}
