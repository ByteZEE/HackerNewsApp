package com.rafiadly.hackernewsapp.data.source.remote.network

import com.rafiadly.hackernewsapp.data.source.remote.response.CommentResponse
import com.rafiadly.hackernewsapp.data.source.remote.response.DetailStoryResponse
import com.rafiadly.hackernewsapp.data.source.remote.response.TopStoryIdsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("item/{storyId}.json?print=pretty")
    suspend fun getDetailStory(
        @Path("storyId") storyId:Int
    ): DetailStoryResponse

    @GET("item/{commentId}.json?print=pretty")
    suspend fun getComment(
        @Path("commentId") commentId:Int
    ): CommentResponse

    @GET("topstories.json?print=pretty")
    suspend fun getTopStoryIds(): TopStoryIdsResponse
}