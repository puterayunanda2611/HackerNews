package com.telkom.hackernews.domain

import com.telkom.hackernews.data.HackerNewsRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopStoriesUseCase @Inject constructor(
    private val repository: HackerNewsRepository,
    private val transformer: GetTopStoriesTransformer
) : BaseUseCase<Unit, Observable<TopStoryModel>> {

    override fun execute(input: Unit): Observable<TopStoryModel> {
        return repository.getTopStoriesId()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { repository.getDetailItem(it).toObservable() }
            .map { transformer.transform(it) }
    }
}
