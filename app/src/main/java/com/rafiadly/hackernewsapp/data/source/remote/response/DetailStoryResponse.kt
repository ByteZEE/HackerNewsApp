package com.rafiadly.hackernewsapp.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.rafiadly.hackernewsapp.data.model.Story
import com.rafiadly.hackernewsapp.utils.DateFormatter

data class DetailStoryResponse(
    @SerializedName("by")
    val `by`: String,
    @SerializedName("descendants")
    val descendants: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kids")
    val kids: List<Int>?,
    @SerializedName("score")
    val score: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("text")
    val text: String?,
    )

fun DetailStoryResponse.toStory() =
    Story(
        id = id,
        title = title,
        comments = descendants,
        score = score,
        date = DateFormatter.timestampToDate(time.toLong()),
        author = by,
        desc = text ?: "No Description",
        commentIds = kids ?: emptyList()
    )