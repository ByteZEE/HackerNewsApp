package com.rafiadly.hackernewsapp.ui.top

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.rafiadly.hackernewsapp.databinding.ActivityTopStoriesBinding
import com.rafiadly.hackernewsapp.ui.ViewModelFactory
import com.rafiadly.hackernewsapp.ui.detail.StoryDetailActivity

class TopStoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}