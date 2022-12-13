package com.telkom.hackernews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.telkom.hackernews.databinding.ActivityTopStoriesBinding

class TopStoriesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTopStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.rvTopStories.apply {
            layoutManager = GridLayoutManager(this@TopStoriesActivity, SPAN_COUNT_GRID)
            adapter = TopStoriesAdapter()
        }
    }

    companion object {
        private const val SPAN_COUNT_GRID = 2
    }
}