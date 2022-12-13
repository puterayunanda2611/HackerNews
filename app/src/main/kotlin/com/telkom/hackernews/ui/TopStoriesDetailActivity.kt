package com.telkom.hackernews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.telkom.hackernews.databinding.ActivityTopStoriesDetailBinding

class TopStoriesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopStoriesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopStoriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(this@TopStoriesDetailActivity)
            adapter = TopStoriesDetailAdapter()
        }
    }
}
