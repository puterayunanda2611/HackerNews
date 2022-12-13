package com.telkom.hackernews.domain

import com.telkom.hackernews.data.ItemResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopStoriesTransformer @Inject constructor()
    : BaseTransformer<ItemResponse, TopStoryModel> {

    override fun transform(input: ItemResponse): TopStoryModel {
        return TopStoryModel(
            id = input.id,
            title = input.title.orEmpty(),
            date = getDateFormat(input.time ?:0L),
            by = input.by.orEmpty(),
            kids = input.kids.orEmpty(),
            score = input.score ?:0
        )
    }

    private fun getDateFormat(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return format.format(date)
    }
}
