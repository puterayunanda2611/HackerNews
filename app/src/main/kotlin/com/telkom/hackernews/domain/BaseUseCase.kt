package com.telkom.hackernews.domain

interface BaseUseCase<I, O> {
    fun execute(input: I): O
}
