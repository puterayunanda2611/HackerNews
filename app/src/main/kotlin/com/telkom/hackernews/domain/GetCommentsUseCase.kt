package com.telkom.hackernews.domain

import com.telkom.hackernews.data.TopStoriesRepository
import com.telkom.hackernews.domain.TopStoryType.COMMENT
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommentsUseCase @Inject constructor(
    private val repository: TopStoriesRepository
) : BaseUseCase<List<Long>, Observable<String>> {

    override fun execute(input: List<Long>): Observable<String> {
        return Observable.just(input)
            .flatMapIterable { it }
            .flatMap { repository.getDetailItem(it).toObservable() }
            .filter { it.type == COMMENT.value }
            .map { it.text }
    }
}
