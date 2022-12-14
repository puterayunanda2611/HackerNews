package com.telkom.hackernews.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.telkom.hackernews.R
import com.telkom.hackernews.databinding.ActivityTopStoriesDetailBinding
import com.telkom.hackernews.di.TopStoryDetailComponent
import com.telkom.hackernews.di.ViewModelFactory
import com.telkom.hackernews.presentation.TopStoriesDetailViewState
import com.telkom.hackernews.presentation.TopStoryDetailViewModel
import com.telkom.hackernews.ui.TopStoryIntentKey.COMMENTS
import com.telkom.hackernews.ui.TopStoryIntentKey.DATE
import com.telkom.hackernews.ui.TopStoryIntentKey.ID
import com.telkom.hackernews.ui.TopStoryIntentKey.TITLE
import com.telkom.hackernews.ui.TopStoryIntentKey.USERNAME
import javax.inject.Inject

class TopStoryDetailActivity : AppCompatActivity() {
    private var id: Long? = null
    private var title: String? = null
    private var date: String? = null
    private var username: String? = null
    private var comments: List<Long>? = null

    private lateinit var binding: ActivityTopStoriesDetailBinding

    @field:Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<TopStoryDetailViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopStoryDetailComponent.ComponentProvider.provide(this).inject(this)
        binding = ActivityTopStoriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        setupListener()
        loadData(intent)
        setupView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putLong(ID, id?:0)
            putString(TITLE, title)
            putString(DATE, date)
            putString(USERNAME, username)
            putLongArray(COMMENTS, comments?.toLongArray())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.apply {
            id = getLong(ID)
            title = getString(TITLE)
            date = getString(DATE)
            username = getString(USERNAME)
            comments = getLongArray(COMMENTS)?.toList()
        }
    }

    private fun observeData() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
                is TopStoriesDetailViewState.Error -> showErrorToast()
                is TopStoriesDetailViewState.Success -> setComments(viewState.text)
            }
        }
    }

    private fun setupListener() {
        binding.acbFavorite.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) viewModel.setFavorite(id = id ?:0, title = title.orEmpty())
        }
    }

    private fun loadData(intent: Intent) {
        id = intent.getLongExtra(ID, 0)
        title = intent.getStringExtra(TITLE)
        date = intent.getStringExtra(DATE)
        username = intent.getStringExtra(USERNAME)
        comments = intent.getLongArrayExtra(COMMENTS)?.toList()

        intent.removeExtra(ID)
        intent.removeExtra(TITLE)
        intent.removeExtra(DATE)
        intent.removeExtra(USERNAME)
        intent.removeExtra(COMMENTS)

        viewModel.getComments(comments.orEmpty())
    }

    private fun setupView() {
        supportActionBar?.hide()
        binding.apply {
            tvTitle.text = title.orEmpty()
            tvDate.text = date.orEmpty()
            tvUsername.text = getString(R.string.detail_page_username_label, username.orEmpty())

            rvComments.apply {
                layoutManager = LinearLayoutManager(this@TopStoryDetailActivity)
                adapter = TopStoryDetailAdapter()
                addItemDecoration(DividerItemDecoration(context, VERTICAL))
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(this, "Error App", Toast.LENGTH_LONG).show()
    }

    private fun setComments(text: String) {
        (binding.rvComments.adapter as? TopStoryDetailAdapter)?.addItem(text)
    }
}
