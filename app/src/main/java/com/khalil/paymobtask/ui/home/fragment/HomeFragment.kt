package com.khalil.paymobtask.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khalil.paymobtask.databinding.FragmentHomeBinding
import com.khalil.paymobtask.ui.home.adabters.MovieLoadStateAdapter
import com.khalil.paymobtask.ui.home.viewmodel.MovieViewModel
import com.mg_group.womniz.Ui.ActivityHome.FragmentHome.Utils.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        collectPagingData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun setupAdapter() {
        movieAdapter = MoviesAdapter(
            onFavoriteClick = { movie ->
                movieViewModel.toggleFavorite(movie)
                movieAdapter.notifyDataSetChanged()
            },
            onMovieClick = { movie ->
                val direction = HomeFragmentDirections
                    .actionHomeFragmentToMovidetailsFragment(movie)
                findNavController().navigate(direction)
            }
        )
        val loadStateAdapter = MovieLoadStateAdapter { movieAdapter.retry() }

        binding.recyclerMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter.withLoadStateFooter(
                footer = loadStateAdapter
            )
            setHasFixedSize(true)
        }

        movieAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

            // Handle errors
            if (loadState.refresh is LoadState.Error) {
                val error = (loadState.refresh as LoadState.Error).error
                showError(error.message ?: "Unknown error")
            }

            // Optional: Show empty state
            if (loadState.refresh is LoadState.NotLoading &&
                movieAdapter.itemCount == 0) {
                showEmptyState()
            } else {
                hideEmptyState()
            }
        }
    }

    private fun collectPagingData() {
        lifecycleScope.launch {
            movieViewModel.moviePagingData.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") { movieAdapter.retry() }
            .show()
    }

    private fun showEmptyState() {
        binding.emptyStateView.visibility = View.VISIBLE
        binding.recyclerMovies.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.emptyStateView.visibility = View.GONE
        binding.recyclerMovies.visibility = View.VISIBLE
    }

}