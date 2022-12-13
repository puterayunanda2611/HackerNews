package com.telkom.hackernews.domain

import com.telkom.hackernews.data.HackerNewsRepository
import io.reactivex.Observable

class GetTopStoriesUseCase(
    private val repository: HackerNewsRepository,
    private val transformer: GetTopStoriesTransformer
) : HackerNewsUseCase<Unit, Observable<HackerNewsModel>> {

    override fun execute(input: Unit): Observable<HackerNewsModel> {
        return repository.getTopStoriesId()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { repository.getDetailItem(it).toObservable() }
            .map { transformer.transform(it) }
    }
}
