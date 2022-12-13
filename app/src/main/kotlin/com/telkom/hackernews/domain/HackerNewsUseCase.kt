package com.telkom.hackernews.domain

interface HackerNewsUseCase<I, O> {
    fun execute(input: I): O
}
