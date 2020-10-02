package com.aayush.telewise.ui.fragment.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aayush.telewise.R
import com.aayush.telewise.databinding.FragmentMoviesBinding
import com.aayush.telewise.util.android.adapter.ItemLoadStateAdapter
import com.aayush.telewise.util.android.log
import com.aayush.telewise.util.android.toast
import com.aayush.telewise.util.android.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel by viewModels<MoviesViewModel>()

    private lateinit var adapter: ListMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_movies, R.id.nav_tv, R.id.nav_people))
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.layoutToolbar.toolbar)
        binding.layoutToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)

        initAdapter()

        binding.btnRetry.setOnClickListener { adapter.retry() }
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        lifecycleScope.launch {
            viewModel.popularMoviesFlow.collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    private fun initAdapter() {
        adapter = ListMovieAdapter()
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.recyclerMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMovies.adapter = adapter.withLoadStateHeaderAndFooter(
            ItemLoadStateAdapter { adapter.retry() },
            ItemLoadStateAdapter { adapter.retry() }
        )
        binding.recyclerMovies.setHasFixedSize(true)

        adapter.addLoadStateListener { loadState ->
            binding.recyclerMovies.isVisible = loadState.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                toast(requireContext(), it.error.toString())
                it.error.log()
            }
        }
    }
}
