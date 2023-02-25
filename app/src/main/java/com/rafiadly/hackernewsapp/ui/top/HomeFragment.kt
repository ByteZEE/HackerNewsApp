package com.rafiadly.hackernewsapp.ui.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rafiadly.hackernewsapp.R
import com.rafiadly.hackernewsapp.databinding.FragmentHomeBinding
import com.rafiadly.hackernewsapp.ui.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var topStoryAdapter: TopStoryAdapter
    private lateinit var viewModel: TopStoriesViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TopStoriesViewModel::class.java]

        topStoryAdapter = TopStoryAdapter {
            val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            toDetailFragment.id = it.id
            view.findNavController().navigate(toDetailFragment)
        }

        binding.rvTopstories.adapter = topStoryAdapter

        viewModel.topStories.observe(viewLifecycleOwner) {
            topStoryAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.favStory.observe(viewLifecycleOwner){ story ->
            binding.myFav.visibility = if(story.id != 0) View.VISIBLE else View.GONE
            binding.tvStoryFav.visibility = if(story.id != 0) View.VISIBLE else View.GONE
            binding.tvStoryFav.text = story.title
            binding.tvStoryFav.setOnClickListener {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                toDetailFragment.id = story.id
                view.findNavController().navigate(toDetailFragment)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavStory()
    }

}