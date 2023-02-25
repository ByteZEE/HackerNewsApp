package com.rafiadly.hackernewsapp.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rafiadly.hackernewsapp.data.repository.StoryRepository
import com.rafiadly.hackernewsapp.di.Injection
import com.rafiadly.hackernewsapp.ui.detail.StoryDetailViewModel
import com.rafiadly.hackernewsapp.ui.top.TopStoriesViewModel

class ViewModelFactory private constructor(private val storyRepository: StoryRepository)
    : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(TopStoriesViewModel::class.java)->{
                TopStoriesViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(StoryDetailViewModel::class.java)->{
                StoryDetailViewModel(storyRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}