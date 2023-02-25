package com.rafiadly.hackernewsapp.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafiadly.hackernewsapp.data.model.Story
import com.rafiadly.hackernewsapp.databinding.ItemTopStoryBinding

class TopStoryAdapter(
    private val onClickItem:(Story) -> Unit
): ListAdapter<Story, TopStoryAdapter.TopStoryViewHolder>(DIFF_CALLBACK) {

    inner class TopStoryViewHolder(private val binding: ItemTopStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Story){
            binding.tvTitle.text = item.title
            binding.tvComments.text = "${item.comments} comments"
            binding.tvScore.text = "Score : ${item.score}"

            binding.root.setOnClickListener {
                onClickItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoryViewHolder {
        val binding =
            ItemTopStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopStoryViewHolder, position: Int) {
        val item = getItem(position)
        if(item!=null){
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}