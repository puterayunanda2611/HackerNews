package com.telkom.hackernews.domain

data class TopStoryModel(
    val id: Long,
    val title: String,
    val date: String,
    val by: String,
    val kids: List<Long>,
    val score: Int
)
