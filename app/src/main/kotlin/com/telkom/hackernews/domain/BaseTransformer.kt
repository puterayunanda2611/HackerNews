package com.telkom.hackernews.domain

interface BaseTransformer<I, O> {
    fun transform(input: I): O
}
