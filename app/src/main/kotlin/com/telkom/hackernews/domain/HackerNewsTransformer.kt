package com.telkom.hackernews.domain

interface HackerNewsTransformer<I, O> {
    fun transform(input: I): O
}
