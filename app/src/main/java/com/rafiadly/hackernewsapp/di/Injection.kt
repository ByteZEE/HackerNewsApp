package com.rafiadly.hackernewsapp.di

import android.content.Context
import com.rafiadly.hackernewsapp.data.repository.StoryRepository
import com.rafiadly.hackernewsapp.data.source.local.preferences.Preferences
import com.rafiadly.hackernewsapp.data.source.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val api = ApiConfig.getApiService()
        val pref = Preferences.getInstance(context)
        return StoryRepository.getInstance(api,pref)
    }
}