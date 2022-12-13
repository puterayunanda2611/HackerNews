package com.telkom.hackernews.data

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("by")
    val by: String,
    @SerializedName("descendants")
    val descendants: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("kids")
    val kids: List<Long>,
    @SerializedName("score")
    val score: Int,
    @SerializedName("time")
    val time: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)
