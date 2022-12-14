package com.telkom.hackernews.domain

import com.telkom.hackernews.data.TopStoriesRepository
import com.telkom.hackernews.domain.TopStoryType.STORY
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopStoriesUseCase @Inject constructor(
    private val repository: TopStoriesRepository,
    private val transformer: GetTopStoriesTransformer
) : BaseUseCase<Unit, Observable<TopStoryModel>> {

    override fun execute(input: Unit): Observable<TopStoryModel> {
        return repository.getTopStoriesId()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { repository.getDetailItem(it).toObservable() }
            .filter { it.type == STORY.value }
            .map { transformer.transform(it) }
    }
}
