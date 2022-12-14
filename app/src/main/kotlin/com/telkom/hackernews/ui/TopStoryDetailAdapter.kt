package com.telkom.hackernews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telkom.hackernews.databinding.ItemCommentStoryBinding
import com.telkom.hackernews.ui.TopStoryDetailAdapter.ViewHolder

class TopStoryDetailAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: String) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(
        private val binding: ItemCommentStoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: String) {
            binding.tvTitle.text = comment
        }

        companion object {
            fun inflate(parent: ViewGroup): ViewHolder {
                return LayoutInflater.from(parent.context)
                    .let { ItemCommentStoryBinding.inflate(it, parent, false) }
                    .let { ViewHolder(it) }
            }
        }
    }
}
