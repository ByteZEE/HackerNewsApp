package com.rafiadly.hackernewsapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rafiadly.hackernewsapp.R
import com.rafiadly.hackernewsapp.data.model.Story
import com.rafiadly.hackernewsapp.databinding.FragmentDetailBinding
import com.rafiadly.hackernewsapp.ui.ViewModelFactory
import com.rafiadly.hackernewsapp.utils.Resource

class DetailFragment : Fragment() {

    private lateinit var viewModel: StoryDetailViewModel
    private lateinit var commentAdapter: CommentAdapter
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[StoryDetailViewModel::class.java]

        commentAdapter = CommentAdapter {

        }
        binding.rvComment.adapter = commentAdapter

        val idStory = DetailFragmentArgs.fromBundle(arguments as Bundle).id

        viewModel.setStoryId(idStory)

        viewModel.story.observe(viewLifecycleOwner){it ->
            if (it is Resource.Success){
                val story = it.data
                binding.tvTitle.text = story!!.title
                binding.tvAuthor.text = "By: ${story.author}"
                binding.tvTime.text = story.date
                binding.tvDesc.text = story.desc
                viewModel.setListComment(story.commentIds)

                viewModel.favStory.observe(viewLifecycleOwner) { favStory ->
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


        viewModel.listComment.observe(viewLifecycleOwner) {
            commentAdapter.submitList(it)
            Log.d("Rafi", "onCreate: ${it.size}")
        }

        viewModel.loading.observe(viewLifecycleOwner) {
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
}