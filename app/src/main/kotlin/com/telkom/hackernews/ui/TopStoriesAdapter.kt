package com.telkom.hackernews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telkom.hackernews.R
import com.telkom.hackernews.databinding.ItemTopStoryBinding
import com.telkom.hackernews.domain.TopStoryModel
import com.telkom.hackernews.ui.TopStoriesAdapter.ViewHolder

class TopStoriesAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var items: MutableList<TopStoryModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clearAllAndAdd(items: List<TopStoryModel>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(
        private val binding: ItemTopStoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TopStoryModel) {
            binding.tvTitle.text = binding.root.context.getString(R.string.main_page_content_title, item.title)
            binding.tvCommentCount.text = binding.root.context.getString(R.string.main_page_content_comment, item.kids.size.toString())
            binding.tvScore.text = binding.root.context.getString(R.string.main_page_content_score, item.score.toString())
        }

        companion object {
            fun inflate(parent: ViewGroup): ViewHolder {
                return LayoutInflater.from(parent.context)
                    .let { ItemTopStoryBinding.inflate(it, parent, false) }
                    .let { ViewHolder(it) }
            }
        }
    }
}
