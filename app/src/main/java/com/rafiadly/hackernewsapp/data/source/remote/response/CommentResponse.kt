package com.rafiadly.hackernewsapp.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.rafiadly.hackernewsapp.data.model.Comment

data class CommentResponse(
    @SerializedName("by")
    val `by`: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kids")
    val kids: List<Int>,
    @SerializedName("parent")
    val parent: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("time")
    val time: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("deleted")
    val deleted: Boolean?,

    )

fun CommentResponse.toComment() =
    Comment(
        text = text,
        author = by
    )