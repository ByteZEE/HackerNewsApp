package com.rafiadly.hackernewsapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.rafiadly.hackernewsapp.R
import com.rafiadly.hackernewsapp.data.model.Story
import com.rafiadly.hackernewsapp.databinding.ActivityStoryDetailBinding
import com.rafiadly.hackernewsapp.ui.ViewModelFactory
import com.rafiadly.hackernewsapp.utils.Resource

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding
    private lateinit var viewModel: StoryDetailViewModel
    private lateinit var commentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Story Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[StoryDetailViewModel::class.java]

        commentAdapter = CommentAdapter {

        }
        binding.rvComment.adapter = commentAdapter

        if (intent.extras != null) {
            val storyId = intent.extras?.getInt("STORY")!!
            viewModel.setStoryId(storyId)
        }
        viewModel.story.observe(this){it ->
            if (it is Resource.Success){
                val story = it.data
                binding.tvTitle.text = story!!.title
                binding.tvAuthor.text = "By: ${story.author}"
                binding.tvTime.text = story.date
                binding.tvDesc.text = story.desc
                viewModel.setListComment(story.commentIds)

                viewModel.favStory.observe(this) { favStory ->
                    val isFavorite = favStory.id == story.id
                    setButtonFav(isFavorite)
                    binding.btnFav.setOnClickListener {
                        setButtonFav(!isFavorite)
                        if (isFavorite){
                            viewModel.setFavStory(
                                Story(id = 0, title = "")
                            )
                        }else{
                            viewModel.setFavStory(story)
                        }
                    }
                }
            }
        }


        viewModel.listComment.observe(this) {
            commentAdapter.submitList(it)
            Log.d("Rafi", "onCreate: ${it.size}")
        }

        viewModel.loading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setButtonFav(isFav:Boolean){
        binding.btnFav.setImageResource(
            if (isFav) R.drawable.ic_star_filled
            else R.drawable.ic_star_outline
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}