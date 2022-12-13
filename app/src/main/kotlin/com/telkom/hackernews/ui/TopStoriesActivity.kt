package com.telkom.hackernews.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.GridLayoutManager
import com.telkom.hackernews.databinding.ActivityTopStoriesBinding
import com.telkom.hackernews.di.TopStoriesComponent
import com.telkom.hackernews.di.ViewModelFactory
import com.telkom.hackernews.domain.TopStoryModel
import com.telkom.hackernews.presentation.TopStoriesViewModel
import com.telkom.hackernews.presentation.TopStoriesViewState
import javax.inject.Inject

class TopStoriesActivity : AppCompatActivity() {
    @field:Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<TopStoriesViewModel> { viewModelFactory }

    private lateinit var binding:ActivityTopStoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopStoriesComponent.ComponentProvider.provide(this).inject(this)
        binding = ActivityTopStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        loadData()
        setupView()
    }

    private fun observeData() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
                is TopStoriesViewState.Error -> showErrorToast()
                is TopStoriesViewState.GetMyFavorite -> setTitle(viewState.title)
                is TopStoriesViewState.Success -> setTopStories(viewState.items)
                TopStoriesViewState.HideLoading -> hideLoading()
                TopStoriesViewState.ShowLoading -> showLoading()
            }
        }
    }

    private fun loadData() {
        viewModel.getFavorite()
        viewModel.loadTopStories()
    }

    private fun setupView() {
        supportActionBar?.hide()
        binding.rvTopStories.apply {
            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
            layoutManager = GridLayoutManager(this@TopStoriesActivity, SPAN_COUNT_GRID)
            adapter = TopStoriesAdapter()
        }
    }

    private fun showErrorToast() {
        Toast.makeText(this, "Error App", Toast.LENGTH_LONG).show()
    }

    private fun setTitle(title: String) {
        binding.tvTitle.isVisible = title.isNotEmpty()
        binding.tvTitle.text = title
    }

    private fun setTopStories(item: TopStoryModel) {
        (binding.rvTopStories.adapter as? TopStoriesAdapter)?.addItem(item)
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    companion object {
        private const val SPAN_COUNT_GRID = 2
    }
}
